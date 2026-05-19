import { HeartPulse } from 'lucide-react';

const HealthScore = ({ health, loading }) => {
  const fmt = (v) =>
    new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(v || 0);

  const getBadgeClass = (status) => {
    switch (status?.toUpperCase()) {
      case 'EXCELLENT': return 'badge-excellent';
      case 'GOOD': return 'badge-good';
      case 'AVERAGE': return 'badge-average';
      case 'POOR': return 'badge-poor';
      default: return 'badge-average';
    }
  };

  const getBarColor = (status) => {
    switch (status?.toUpperCase()) {
      case 'EXCELLENT': return '#10b981';
      case 'GOOD': return '#3b82f6';
      case 'AVERAGE': return '#f59e0b';
      case 'POOR': return '#f43f5e';
      default: return '#94a3b8';
    }
  };

  if (loading) {
    return (
      <div className="glass-card health-card">
        <div className="loading-wrap"><div className="spinner" /></div>
      </div>
    );
  }

  const pct = health ? Math.min(Math.max(health.savingsPercentage, 0), 100).toFixed(1) : 0;

  return (
    <div className="glass-card health-card">
      <div className="health-header">
        <span className="health-title" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <HeartPulse size={20} color="var(--accent-red)" /> Financial Health
        </span>
        {health && (
          <span className={`health-badge ${getBadgeClass(health.healthStatus)}`}>
            {health.healthStatus}
          </span>
        )}
      </div>

      {health ? (
        <>
          <div className="health-percentage" style={{ color: getBarColor(health.healthStatus) }}>
            {pct}%
          </div>
          <div style={{ fontSize: '0.78rem', color: 'var(--text-muted)', marginBottom: '12px' }}>
            Savings Rate
          </div>
          <div className="health-bar-wrap">
            <div
              className="health-bar-fill"
              style={{ width: `${pct}%`, background: getBarColor(health.healthStatus) }}
            />
          </div>
          <div className="health-stats">
            <div className="health-stat">
              <strong style={{ color: '#10b981' }}>{fmt(health.totalIncome)}</strong>
              Income
            </div>
            <div className="health-stat">
              <strong style={{ color: '#f43f5e' }}>{fmt(health.totalExpense)}</strong>
              Expense
            </div>
            <div className="health-stat">
              <strong style={{ color: '#3b82f6' }}>{fmt(health.savings)}</strong>
              Savings
            </div>
          </div>
        </>
      ) : (
        <div style={{ color: 'var(--text-muted)', fontSize: '0.875rem' }}>
          No health data available yet.
        </div>
      )}
    </div>
  );
};

export default HealthScore;
