import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import InsightsPanel from '../components/InsightsPanel';
import { getInsights } from '../api/dashboardApi';
import { Bot } from 'lucide-react';

const InsightsPage = () => {
  const { user } = useAuth();
  const [insights, setInsights] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.userId) return;

    setLoading(true);
    getInsights(user.userId)
      .then(d => { setInsights(d); setLoading(false); })
      .catch(() => setLoading(false));
  }, [user?.userId]);

  return (
    <div className="insights-page">
      <div className="dashboard-header" style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
        <div style={{
          width: '64px',
          height: '64px',
          borderRadius: '50%',
          background: 'linear-gradient(135deg, var(--accent-amber), var(--accent-red))',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          fontSize: '2rem',
          boxShadow: '0 0 20px rgba(245, 158, 11, 0.4)',
          animation: 'pulseGlow 3s infinite'
        }}>
          <Bot size={32} color="#fff" />
        </div>
        <div>
          <h2>AI Financial Coach</h2>
          <p>Personalized suggestions and trend analysis based on your spending.</p>
        </div>
      </div>

      <div className="dashboard-grid">
        <div style={{ maxWidth: '900px' }}>
          <InsightsPanel insights={insights} loading={loading} />
        </div>
      </div>
    </div>
  );
};

export default InsightsPage;
