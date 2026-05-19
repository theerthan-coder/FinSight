import { Pencil, Trash2, ReceiptText } from 'lucide-react';

const TransactionList = ({ transactions, loading, onEdit, onDelete }) => {
  const fmt = (v) =>
    new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(v);

  const formatDate = (d) => {
    return new Intl.DateTimeFormat('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    }).format(new Date(d));
  };

  const getTypeClass = (type) => {
    switch (type) {
      case 'INCOME': return 'type-income';
      case 'EXPENSE': return 'type-expense';
      case 'BONUS': return 'type-bonus';
      default: return '';
    }
  };

  const getAmountClass = (type) => {
    switch (type) {
      case 'INCOME': return 'tx-amount-income';
      case 'EXPENSE': return 'tx-amount-expense';
      case 'BONUS': return 'tx-amount-bonus';
      default: return '';
    }
  };

  if (loading) {
    return (
      <div className="glass-card" style={{ padding: '28px' }}>
        <div className="loading-wrap"><div className="spinner" /></div>
      </div>
    );
  }

  return (
    <div className="glass-card" style={{ padding: '28px' }}>
      <div className="tx-table-wrap">
        {transactions?.length > 0 ? (
          <table className="tx-table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Category</th>
                <th>Note</th>
                <th>Type</th>
                <th style={{ textAlign: 'right' }}>Amount</th>
                <th style={{ textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map(t => (
                <tr key={t.id}>
                  <td>{formatDate(t.date)}</td>
                  <td><strong style={{ color: 'var(--text-primary)' }}>{t.category}</strong></td>
                  <td>{t.note || '-'}</td>
                  <td>
                    <span className={`tx-type-badge ${getTypeClass(t.type)}`}>
                      {t.type}
                    </span>
                  </td>
                  <td style={{ textAlign: 'right' }} className={getAmountClass(t.type)}>
                    {t.type === 'EXPENSE' ? '-' : '+'}{fmt(t.amount)}
                  </td>
                  <td>
                    <div className="tx-actions" style={{ justifyContent: 'flex-end' }}>
                      <button className="btn btn-ghost btn-sm" onClick={() => onEdit(t)}>
                        <Pencil size={14} /> Edit
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => onDelete(t.id)}>
                        <Trash2 size={14} />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <div className="tx-empty">
            <div className="tx-empty-icon"><ReceiptText size={48} color="var(--text-muted)" /></div>
            <div style={{ color: 'var(--text-primary)', fontWeight: '600', marginBottom: '4px' }}>
              No transactions yet
            </div>
            <div>Add a transaction to see it here.</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default TransactionList;
