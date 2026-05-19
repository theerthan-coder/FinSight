import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { LayoutDashboard, LineChart, CreditCard, Lightbulb, ArrowRight } from 'lucide-react';

const Home = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const navCards = [
    {
      title: "Financial Overview",
      description: "View your high-level summary, income vs. expenses, and financial health score.",
      icon: <LayoutDashboard size={28} />,
      path: "/overview",
      color: "var(--accent-green)"
    },
    {
      title: "Expense Analytics",
      description: "Deep dive into your spending categories and visual charts.",
      icon: <LineChart size={28} />,
      path: "/analytics",
      color: "var(--accent-blue)"
    },
    {
      title: "Transactions",
      description: "Manage, filter, and add your recent transactions.",
      icon: <CreditCard size={28} />,
      path: "/transactions",
      color: "var(--accent-purple)"
    },
    {
      title: "AI Insights",
      description: "Get personalized suggestions and trend analysis from your financial coach.",
      icon: <Lightbulb size={28} />,
      path: "/insights",
      color: "var(--accent-amber)"
    }
  ];

  return (
    <div className="home-container">
      <div className="dashboard-header">
        <h2>Command Center</h2>
        <p>Welcome back, {user?.name || 'User'}. Where would you like to go today?</p>
      </div>

      <div className="home-grid">
        {navCards.map((card, idx) => (
          <div 
            key={idx} 
            className="glass-card nav-card"
            onClick={() => navigate(card.path)}
            style={{ '--card-color': card.color }}
          >
            <div className="nav-card-icon" style={{ background: `linear-gradient(135deg, ${card.color}, transparent)` }}>
              {card.icon}
            </div>
            <h3>{card.title}</h3>
            <p>{card.description}</p>
            <div className="nav-card-arrow"><ArrowRight size={20} /></div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
