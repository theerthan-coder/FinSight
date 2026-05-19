import { useState, useEffect } from 'react';

const TransactionForm = ({ isOpen, onClose, onSubmit, initialData }) => {
  const [formData, setFormData] = useState({
    amount: '',
    type: 'EXPENSE',
    category: '',
    date: new Date().toISOString().split('T')[0],
    note: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (initialData) {
      setFormData({
        amount: initialData.amount,
        type: initialData.type,
        category: initialData.category,
        date: initialData.date,
        note: initialData.note || ''
      });
    } else {
      setFormData({
        amount: '',
        type: 'EXPENSE',
        category: '',
        date: new Date().toISOString().split('T')[0],
        note: ''
      });
    }
  }, [initialData, isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    
    if (!formData.amount || formData.amount <= 0) {
      setError('Please enter a valid amount greater than 0');
      return;
    }
    if (!formData.category.trim()) {
      setError('Please enter a category');
      return;
    }

    setLoading(true);
    try {
      await onSubmit({
        ...formData,
        amount: parseFloat(formData.amount)
      });
      onClose();
    } catch (err) {
      setError(err.message || 'Failed to save transaction');
    } finally {
      setLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-card" onClick={e => e.stopPropagation()}>
        <div className="modal-header">
          <div className="modal-title">
            {initialData ? 'Edit Transaction' : 'Add Transaction'}
          </div>
          <button className="modal-close" onClick={onClose}>&times;</button>
        </div>

        {error && <div className="alert-error">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Type</label>
            <select 
              name="type" 
              className="form-input" 
              value={formData.type} 
              onChange={handleChange}
            >
              <option value="EXPENSE">Expense</option>
              <option value="INCOME">Income</option>
              <option value="BONUS">Bonus</option>
            </select>
          </div>

          <div className="form-group">
            <label className="form-label">Amount</label>
            <input 
              type="number" 
              name="amount" 
              className="form-input" 
              placeholder="0.00"
              step="0.01"
              min="0.01"
              value={formData.amount} 
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Category</label>
            <input 
              type="text" 
              name="category" 
              className="form-input" 
              placeholder="e.g. Food, Salary, Rent"
              value={formData.category} 
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Date</label>
            <input 
              type="date" 
              name="date" 
              className="form-input" 
              value={formData.date} 
              onChange={handleChange}
              max={new Date().toISOString().split('T')[0]}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Note (Optional)</label>
            <input 
              type="text" 
              name="note" 
              className="form-input" 
              placeholder="Brief description"
              value={formData.note} 
              onChange={handleChange}
            />
          </div>

          <div style={{ display: 'flex', gap: '12px', marginTop: '24px' }}>
            <button 
              type="button" 
              className="btn btn-ghost" 
              style={{ flex: 1, justifyContent: 'center' }}
              onClick={onClose}
              disabled={loading}
            >
              Cancel
            </button>
            <button 
              type="submit" 
              className="btn btn-primary" 
              style={{ flex: 2, justifyContent: 'center' }}
              disabled={loading}
            >
              {loading ? 'Saving...' : 'Save Transaction'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default TransactionForm;
