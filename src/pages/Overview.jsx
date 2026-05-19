import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import SummaryCards from '../components/SummaryCards';
import HealthScore from '../components/HealthScore';
import { getSummary, getHealth } from '../api/dashboardApi';

const Overview = () => {
  const { user } = useAuth();
  
  const [summary, setSummary] = useState(null);
  const [health, setHealth] = useState(null);
  const [loading, setLoading] = useState({ summary: true, health: true });

  useEffect(() => {
    if (!user?.userId) return;

    getSummary(user.userId)
      .then(d => { setSummary(d); setLoading(p => ({...p, summary: false})); })
      .catch(() => setLoading(p => ({...p, summary: false})));

    getHealth(user.userId)
      .then(d => { setHealth(d); setLoading(p => ({...p, health: false})); })
      .catch(() => setLoading(p => ({...p, health: false})));
  }, [user?.userId]);

  return (
    <>
      <div className="dashboard-header">
        <h2>Dashboard Overview</h2>
        <p>Welcome back! Here's what's happening with your finances.</p>
      </div>

      <div className="dashboard-grid">
        <SummaryCards summary={summary} loading={loading.summary} />
        
        <div className="dashboard-row-2">
           <HealthScore health={health} loading={loading.health} />
           <div className="glass-card" style={{ padding: '28px', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--text-muted)' }}>
              More overview widgets coming soon...
           </div>
        </div>
      </div>
    </>
  );
};

export default Overview;
