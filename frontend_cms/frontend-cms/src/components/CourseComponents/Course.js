import React from "react";
import '../../App.css';
import { Link } from "react-router-dom";

function Course() {
  return (
    <div className="course">
      <h2 className="course-title">COURSE INFORMATION</h2>
      <div className="course-menus" >
        <button className="menu-button">
          <Link to="/course/addcourse">Add Course</Link>
        </button>
        <button className="menu-button">
          <Link to="/course/updatecourse">Update Course</Link>
        </button>
        <button className="menu-button">
          <Link to="/course/viewcourse">View Course</Link>
        </button>
        <button className="menu-button">
          <Link to="/course/viewfeedback">View Feedback Rating</Link>
        </button>
        <button className="menu-button">
          <Link to="/course/deactivate">Course Deactivate</Link>
        </button>
        <button className="menu-button">
          <Link to="/course/viewAll">viewAll</Link>
        </button>
      </div>
    </div>
  );
}

export default Course;
