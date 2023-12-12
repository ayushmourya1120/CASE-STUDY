import React from 'react';
import '../../App.css'
import { Link, Outlet } from 'react-router-dom';

const Associate = () => {
  return (
    <div>
      <h2 className="course-title">ASSOCIATE INFO</h2>
      <div className="course-menus">
        <button className="menu-button">
          <Link to="/associate/addassociate">Add Associate</Link>
        </button>
        <button className="menu-button">
          <Link to="/associate/updateassociate">Update Associate</Link>
        </button>
        <button className="menu-button">
          <Link to="/associate/viewassociate">View Associate</Link>
        </button>
        <button className="menu-button">
          <Link to="/associate/viewallassociate">View All Associates</Link>
        </button>
      </div>
      <Outlet />
    </div>
  );
};

export default Associate;
