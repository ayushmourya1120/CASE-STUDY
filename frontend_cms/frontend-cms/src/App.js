import React from 'react';

import './App.css';

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import Routing from './components/Routing';


const App = () => {
  return (
    <Router>

      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

        <Link to="/" className="navbar-brand">

          TekGain

        </Link>

        <button

          className="navbar-toggler"

          type="button"

          data-toggle="collapse"

          data-target="#navbarNav"

          aria-controls="navbarNav"

          aria-expanded="false"

          aria-label="Toggle navigation"

        >

          <span className="navbar-toggler-icon"></span>

        </button>

        <div className="collapse navbar-collapse justify-content-end" id="navbarNav">

          <ul className="navbar-nav">

            <li className="nav-item">

              <Link to="/course" className="nav-link">

                Course

              </Link>

            </li>

            <li className="nav-item">

              <Link to="/associate" className="nav-link">

                Associate

              </Link>

            </li>

            <li className="nav-item">

              <Link to="/admission" className="nav-link">

                Admission

              </Link>

            </li>


          </ul>

        </div>

      </nav>

      <Routes>

        <Route path="/*" element={<Routing />} />

      </Routes>

      <footer className="footer">

        <div className="container text-center">

          <span className="text-muted">© 2023 TekGain. All rights reserved.</span>

        </div>

      </footer>

    </Router>


  );
};




export default App;