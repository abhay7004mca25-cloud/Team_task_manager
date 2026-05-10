import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { LayoutDashboard, FolderKanban, CheckSquare, LogOut } from 'lucide-react';

const Navbar = () => {
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/login');
  };

  return (
    <nav className="bg-slate-800 border-b border-slate-700 p-4 sticky top-0 z-50 shadow-lg shadow-black/20">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-xl font-bold bg-gradient-to-r from-blue-400 to-indigo-500 bg-clip-text text-transparent flex items-center gap-2">
          <div className="w-8 h-8 rounded-lg bg-gradient-to-tr from-blue-500 to-indigo-500 flex items-center justify-center text-white shadow-lg shadow-blue-500/30">
             TM
          </div>
          TaskFlow
        </Link>
        <div className="flex gap-6 items-center">
          <Link to="/" className="text-slate-300 hover:text-white transition flex items-center gap-2"><LayoutDashboard size={18}/> Dashboard</Link>
          <Link to="/projects" className="text-slate-300 hover:text-white transition flex items-center gap-2"><FolderKanban size={18}/> Projects</Link>
          <Link to="/tasks" className="text-slate-300 hover:text-white transition flex items-center gap-2"><CheckSquare size={18}/> Tasks</Link>
          <button onClick={handleLogout} className="ml-4 text-red-400 hover:text-red-300 transition flex items-center gap-2">
            <LogOut size={18}/> Logout
          </button>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
