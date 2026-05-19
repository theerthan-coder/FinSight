import { TrendingUp, TrendingDown, Landmark } from 'lucide-react';

const SummaryCards = ({ summary, loading }) => {
  const fmt = (v) =>
    new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(v || 0);

  if (loading) {
    return (
      <div className="dashboard-row">
        {[1, 2, 3].map((i) => (
          <div key={i} className="glass-card summary-card">
            <div className="loading-wrap"><div className="spinner" /></div>
          </div>
        ))}
      </div>
    );
  }

  const cards = [
    {
      label: 'Total Income',
      value: fmt(summary?.totalIncome),
      icon: <TrendingUp size={24} />,
      cls: 'income',
      iconCls: 'green',
      valCls: 'green',
    },
    {
      label: 'Total Expense',
      value: fmt(summary?.totalExpense),
      icon: <TrendingDown size={24} />,
      cls: 'expense',
      iconCls: 'red',
      valCls: 'red',
    },
    {
      label: 'Savings',
      value: fmt(summary?.savings),
      icon: <Landmark size={24} />,
      cls: 'savings',
      iconCls: 'blue',
      valCls: summary?.savings >= 0 ? 'green' : 'red',
    },
  ];

  return (
    <div className="dashboard-row">
      {cards.map((c) => (
        <div key={c.label} className={`glass-card summary-card ${c.cls}`}>
          <div className={`card-icon ${c.iconCls}`}>{c.icon}</div>
          <div className="card-label">{c.label}</div>
          <div className={`card-value ${c.valCls}`}>{summary ? c.value : '—'}</div>
        </div>
      ))}
    </div>
  );
};

export default SummaryCards;
