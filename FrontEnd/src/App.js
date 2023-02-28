import * as React from 'react';
import { Routes, Route, Outlet, NavLink, BrowserRouter } from 'react-router-dom';
import { ClientPage } from './clients/ClientPage';
import { InsurancePage } from './insurance/InsurancePage';
import { AgencyPage } from './agencies/AgencyPage';

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
    <Route element={<Layout />}>
        <Route index element={<ClientPage />} />
        <Route path="/" element={<ClientPage />} />
        <Route path="/insurance" element={<InsurancePage />} />
        <Route path="/agencies" element={<AgencyPage />} />
        <Route path="*" element={<p>There's nothing here: 404!</p>} />
      </Route>
    </Routes>
    </BrowserRouter>
  );
};

const Layout = () => {
  const style = ({ isActive }) => ({
    fontWeight: isActive ? 'bold' : 'normal',
    padding: '1rem'
  });

  return (
    <>
      <h1>Insurance Agency</h1>

      <nav
        style={{
          borderBottom: 'solid 1px',
          padding: '1rem'        }}
      >
       <NavLink to="/" style={style}>
          Clients
        </NavLink>
        <NavLink to="/insurance" style={style}>
          Insurance
        </NavLink>
        <NavLink to="/agencies" style={style}>
          Agency
        </NavLink>
      </nav>



      <main style={{ padding: '1rem 0' }}>
        <Outlet />
      </main>
    </>
  );
};


export default App;