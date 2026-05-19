import { TrendingUp, TrendingDown, ArrowRight, Search, Tag, Lightbulb } from 'lucide-react';

const InsightsPanel = ({ insights, loading }) => {
  const fmt = (v) =>
    new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(v || 0);

  const trendClass = (t) => {
    if (!t) return '';
    return t === 'INCREASED' ? 'trend-increased' : t === 'DECREASED' ? 'trend-decreased' : 'trend-same';
  };

  const trendIcon = (t) => {
    if (t === 'INCREASED') return <TrendingUp size={14} />;
    if (t === 'DECREASED') return <TrendingDown size={14} />;
    return <ArrowRight size={14} />;
  };

  if (loading) {
    return (
      <div className="glass-card insights-card">
        <div className="loading-wrap"><div className="spinner" /></div>
      </div>
    );
  }

  return (
    <div className="glass-card insights-card">
      <div className="section-title" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Search size={20} /> Smart Insights
      </div>

      {insights ? (
        <>
          <div className="insight-row">
            <div className="insight-item">
              <div className="insight-item-label">This Month Expense</div>
              <div className="insight-item-value" style={{ color: '#f43f5e' }}>
                {fmt(insights.currentMonthExpense)}
              </div>
            </div>
            <div className="insight-item">
              <div className="insight-item-label">Last Month Expense</div>
              <div className="insight-item-value">{fmt(insights.lastMonthExpense)}</div>
            </div>
            <div className="insight-item">
              <div className="insight-item-label">This Month Income</div>
              <div className="insight-item-value" style={{ color: '#10b981' }}>
                {fmt(insights.currentMonthIncome)}
              </div>
            </div>
            <div className="insight-item">
              <div className="insight-item-label">Top Category</div>
              <div className="insight-item-value" style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
                <Tag size={16} color="var(--text-muted)" /> {insights.topCategory}
              </div>
            </div>
          </div>

          <div style={{ marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Expense Trend:</span>
            <span className={`trend-badge ${trendClass(insights.trend)}`}>
              {trendIcon(insights.trend)} {insights.trend}
            </span>
          </div>

          {insights.suggestions?.length > 0 && (
            <ul className="suggestions-list">
              {insights.suggestions.map((s, i) => (
                <li key={i} className="suggestion-item" style={{ display: 'flex', gap: '8px' }}>
                  <Lightbulb size={16} style={{ flexShrink: 0, marginTop: '2px', color: 'var(--accent-amber)' }} />
                  <span>{s}</span>
                </li>
              ))}
            </ul>
          )}
        </>
      ) : (
        <div style={{ color: 'var(--text-muted)', fontSize: '0.875rem' }}>
          No insights available. Add more transactions to see patterns.
        </div>
      )}
    </div>
  );
};

export default InsightsPanel;
