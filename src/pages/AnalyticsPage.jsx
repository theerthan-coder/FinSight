import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { getCategorySummary } from '../api/dashboardApi';
import CategoryChart from '../components/CategoryChart';
import { Lightbulb } from 'lucide-react';

const AnalyticsPage = () => {
  const { user } = useAuth();
  const [categoryData, setCategoryData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.userId) return;

    setLoading(true);
    getCategorySummary(user.userId)
      .then(d => { setCategoryData(d); setLoading(false); })
      .catch(() => setLoading(false));
  }, [user?.userId]);

  return (
    <div className="analytics-page">
      <div className="dashboard-header">
        <h2>Expense Analytics</h2>
        <p>Understand where your money is going.</p>
      </div>

      <div className="dashboard-grid">
        <CategoryChart data={categoryData} loading={loading} />
        
        <div className="glass-card" style={{ padding: '28px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div className="section-title" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Lightbulb size={20} color="var(--accent-amber)" /> Spending Insights
          </div>
          {loading ? (
             <div className="spinner"></div>
          ) : categoryData && Object.keys(categoryData).length > 0 ? (
            <div className="suggestions-list">
              {Object.entries(categoryData)
                .sort(([,a], [,b]) => b - a)
                .slice(0, 3)
                .map(([name, val]) => (
                  <div key={name} className="suggestion-item">
                    Your highest spending category is <strong>{name}</strong> (₹{val.toLocaleString('en-IN')}).
                  </div>
              ))}
            </div>
          ) : (
            <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>No spending data available to generate insights.</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AnalyticsPage;
