import { PieChart as RechartsPieChart, Pie, Cell, ResponsiveContainer, Tooltip, Legend } from 'recharts';
import { PieChart } from 'lucide-react';

const CategoryChart = ({ data, loading }) => {
  if (loading) {
    return (
      <div className="glass-card chart-card">
        <div className="loading-wrap"><div className="spinner" /></div>
      </div>
    );
  }

  // Convert map { category: amount } to array [{ name, value }]
  const chartData = data
    ? Object.entries(data).map(([name, value]) => ({ name, value }))
    : [];

  const COLORS = ['#10b981', '#f43f5e', '#3b82f6', '#f59e0b', '#8b5cf6', '#06b6d4', '#ec4899'];

  const CustomTooltip = ({ active, payload }) => {
    if (active && payload && payload.length) {
      const data = payload[0];
      return (
        <div style={{
          background: 'rgba(15, 22, 41, 0.9)',
          border: '1px solid rgba(255,255,255,0.1)',
          padding: '10px 15px',
          borderRadius: '8px',
          color: '#fff',
          fontSize: '0.85rem'
        }}>
          <div style={{ fontWeight: '600', marginBottom: '4px' }}>{data.name}</div>
          <div style={{ color: data.payload.fill }}>
            {new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(data.value)}
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="glass-card chart-card">
      <div className="section-title" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        <PieChart size={20} /> Expense by Category
      </div>
      
      {chartData.length > 0 ? (
        <div style={{ height: '300px', width: '100%' }}>
          <ResponsiveContainer width="100%" height="100%">
            <RechartsPieChart>
              <Pie
                data={chartData}
                cx="50%"
                cy="50%"
                innerRadius={60}
                outerRadius={90}
                paddingAngle={5}
                dataKey="value"
              >
                {chartData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip content={<CustomTooltip />} />
              <Legend verticalAlign="bottom" height={36} iconType="circle" />
            </RechartsPieChart>
          </ResponsiveContainer>
        </div>
      ) : (
        <div style={{ color: 'var(--text-muted)', fontSize: '0.875rem', textAlign: 'center', padding: '40px 0' }}>
          No expense data available for chart.
        </div>
      )}
    </div>
  );
};

export default CategoryChart;
