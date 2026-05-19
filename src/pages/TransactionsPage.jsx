import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import TransactionList from '../components/TransactionList';
import TransactionForm from '../components/TransactionForm';
import { getTransactions, addTransaction, updateTransaction, deleteTransaction } from '../api/transactionApi';
import { Plus } from 'lucide-react';

const TransactionsPage = () => {
  const { user } = useAuth();
  
  const [transactions, setTransactions] = useState([]);
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editTx, setEditTx] = useState(null);

  const fetchTransactions = async () => {
    if (!user?.userId) return;
    try {
      setLoading(true);
      const data = await getTransactions(user.userId);
      const sorted = (data || []).sort((a, b) => new Date(b.date) - new Date(a.date));
      setTransactions(sorted);
      setFilteredTransactions(sorted);
    } catch (error) {
      if(error.message !== 'No transactions found') {
          console.error("Transaction load error", error);
      }
      setTransactions([]);
      setFilteredTransactions([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransactions();
    // eslint-disable-next-line
  }, [user?.userId]);

  useEffect(() => {
    if (searchTerm) {
      const lower = searchTerm.toLowerCase();
      setFilteredTransactions(transactions.filter(t => 
        t.description.toLowerCase().includes(lower) || 
        t.category.toLowerCase().includes(lower)
      ));
    } else {
      setFilteredTransactions(transactions);
    }
  }, [searchTerm, transactions]);

  const handleOpenForm = (tx = null) => {
    setEditTx(tx);
    setIsModalOpen(true);
  };

  const handleCloseForm = () => {
    setEditTx(null);
    setIsModalOpen(false);
  };

  const handleSaveTransaction = async (data) => {
    const payload = { ...data, userId: user.userId };
    if (editTx) {
      await updateTransaction(editTx.id, payload);
    } else {
      await addTransaction(payload);
    }
    fetchTransactions();
  };

  const handleDeleteTransaction = async (id) => {
    if (window.confirm("Are you sure you want to delete this transaction?")) {
      try {
        await deleteTransaction(id);
        fetchTransactions();
      } catch (err) {
        alert("Failed to delete: " + err.message);
      }
    }
  };

  return (
    <div className="transactions-page">
      <div className="dashboard-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end' }}>
        <div>
          <h2>Transactions</h2>
          <p>Manage and track all your income and expenses.</p>
        </div>
        <button className="btn btn-primary" onClick={() => handleOpenForm()}>
          <Plus size={18} /> Add Transaction
        </button>
      </div>

      <div className="glass-card" style={{ padding: '24px', marginBottom: '24px' }}>
        <input 
          type="text" 
          className="form-input" 
          placeholder="Search by description or category..." 
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{ maxWidth: '400px' }}
        />
      </div>

      <div className="tx-section">
         <TransactionList 
           transactions={filteredTransactions} 
           loading={loading} 
           onEdit={handleOpenForm}
           onDelete={handleDeleteTransaction}
         />
      </div>

      <TransactionForm 
        isOpen={isModalOpen}
        onClose={handleCloseForm}
        onSubmit={handleSaveTransaction}
        initialData={editTx}
      />
    </div>
  );
};

export default TransactionsPage;
