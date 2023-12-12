import React from "react";
import { Routes, Route } from "react-router-dom";
import ViewAllCourses from "./CourseComponents/ViewAllCourses";
import Course from "./CourseComponents/Course";
import AddCourse from "./CourseComponents/AddCourse";
import UpdateCourse from "./CourseComponents/UpdateCourse";
import ViewCourse from "./CourseComponents/ViewCourse";
import ViewFeedback from "./CourseComponents/ViewFeedback"
import CourseDeactivate from "./CourseComponents/CourseDeactivate";
import Associate from "./AssociateComponents/Associate";
import AddAssociate from "./AssociateComponents/AddAssociate ";
import UpdateAssociate from "./AssociateComponents/UpdateAssociate";
import ViewAssociate from "./AssociateComponents/ViewAssociate";
import ViewAllAssociate from "./AssociateComponents/viewAllAssociate";
import Admission from "./AdmissionComponents/Admission";
import AssociateRegistration from "./AdmissionComponents/AssociateRegistration ";
import FeeDetails from "./AdmissionComponents/FeeDetails";
import AddFeedbackRating from './AdmissionComponents/AddFeedback';
import HighestFeeCalculation from "./AdmissionComponents/HighestFeeCalculation";
import ViewFeedbackmessage from "./AdmissionComponents/ViewFeedback";
import MakePayment from "./AdmissionComponents/MakePayment";
import ViewAllAdmissions from "./AdmissionComponents/ViewAllRegistration";

function Routing() {
    return (
        <Routes>
        <Route path="/course" element={<Course />} />
        <Route path="/course/viewall" element={<ViewAllCourses />} />
        <Route path="/course/addCourse" element={<AddCourse />} />
        <Route path="/course/updatecourse" element={<UpdateCourse />} />
        <Route path="/course/viewcourse" element={<ViewCourse />} />
        <Route path="/course/viewfeedback" element={<ViewFeedback />} />
        <Route path="/course/deactivate" element={<CourseDeactivate />} />
        <Route path="/associate" element={<Associate />} />
        <Route path="/associate/addassociate" element={<AddAssociate />} />
        <Route path="/associate/updateassociate" element={<UpdateAssociate />} />
        <Route path="/associate/viewassociate" element={<ViewAssociate />} />
        <Route path="/associate/viewallassociate" element={<ViewAllAssociate />} />
        <Route path="/admission" element={<Admission />} />
        <Route path="admission/registration" element={<AssociateRegistration />} />
        <Route path="admission/totalFee" element={<FeeDetails />} />
        <Route path="admission/registration" element={<AssociateRegistration />} />
        <Route path="admission/addfeedback" element={<AddFeedbackRating />} />
        <Route path="admission/highestfee" element={<HighestFeeCalculation />} />
        <Route path="admission/viewfeedback" element={<ViewFeedbackmessage />} />
        <Route path="admission/viewall" element={<ViewAllAdmissions />} />
        <Route path="admission/makepayment" element={<MakePayment />} />





      </Routes>
  );
}

export default Routing;