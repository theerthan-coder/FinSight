import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Wallet, Home, LayoutDashboard, LineChart, CreditCard, Lightbulb, User } from 'lucide-react';

const Sidebar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <NavLink className="sidebar-brand" to="/home">
          <div className="brand-icon"><Wallet size={20} color="#fff" /></div>
          <span>Finsight</span>
        </NavLink>
      </div>

      <div className="sidebar-nav">
        <div className="nav-group-title">Menu</div>
        <NavLink to="/home" className={({isActive}) => isActive ? "sidebar-link active" : "sidebar-link"}>
          <span className="link-icon"><Home size={20} /></span>
          Home
        </NavLink>
        <NavLink to="/overview" className={({isActive}) => isActive ? "sidebar-link active" : "sidebar-link"}>
          <span className="link-icon"><LayoutDashboard size={20} /></span>
          Overview
        </NavLink>
        <NavLink to="/analytics" className={({isActive}) => isActive ? "sidebar-link active" : "sidebar-link"}>
          <span className="link-icon"><LineChart size={20} /></span>
          Analytics
        </NavLink>
        <NavLink to="/transactions" className={({isActive}) => isActive ? "sidebar-link active" : "sidebar-link"}>
          <span className="link-icon"><CreditCard size={20} /></span>
          Transactions
        </NavLink>
        <NavLink to="/insights" className={({isActive}) => isActive ? "sidebar-link active" : "sidebar-link"}>
          <span className="link-icon"><Lightbulb size={20} /></span>
          Insights
        </NavLink>
      </div>

      {user && (
        <div className="sidebar-footer">
          <div className="user-profile">
            <div className="user-avatar"><User size={20} /></div>
            <div className="user-info">
              <span className="user-name">{user.name}</span>
              <span className="user-email">Premium User</span>
            </div>
          </div>
          <button className="btn-logout" onClick={handleLogout}>
            Logout
          </button>
        </div>
      )}
    </aside>
  );
};

export default Sidebar;
