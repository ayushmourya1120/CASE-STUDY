import React from 'react';
import '../../App.css';
import { Link, Outlet } from 'react-router-dom';

const Admission = () => {
  return (
    <div className="admission">
      <h2 className="course-title">ADMISSION DETAILS</h2>
      <div className="course-menus">
        <button className="menu-button">
          <Link to="/admission/registration" className="nav-link">Associate Registration</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/totalFee" className="nav-link">Calculate Total Fee</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/addFeedback" className="nav-link">Add Feedback</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/highestFee" className="nav-link">Highest Fee</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/viewFeedback" className="nav-link">View Feedback</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/viewall" className="nav-link">ViewAll</Link>
        </button>
        <button className="menu-button">
          <Link to="/admission/makePayment" className="nav-link">Make Payment</Link>
        </button>
      </div>
      <Outlet />
    </div>
  );
};

export default Admission;
