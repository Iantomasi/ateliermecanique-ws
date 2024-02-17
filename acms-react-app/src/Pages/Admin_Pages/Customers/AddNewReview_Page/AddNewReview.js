import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar';
import Footer from '../../../../Components/Footer/Footer';
import customerService from '../../../../Services/customer.service';

function AddReview() {
    const [reviewDetails, setReviewDetails] = useState({
        reviewId: '',
        customerId: '',
        appointmentId: '',
        comment: '',
        rating: 0.0,
        reviewDate: '',
        mechanicReply: ''
    });



    const navigate = useNavigate();

    function handleInputChange(event) {
        const { name, value } = event.target;
        setReviewDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function submitReview(event) {
        event.preventDefault();

        customerService.addReview(reviewDetails)
            .then(response => {
                if (response.status === 201) { // Assuming 201 Created is the response for a successful add
                    console.log("Review successfully added!");
                    alert("Thank you for your review!");
                    navigate('/reviews'); // Adjust as necessary to navigate to the appropriate page
                }
            })
            .catch(error => {
                console.error("Error submitting review", error);
                alert("Failed to submit review. Please try again.");
            });
    }

    return (
        <div className="flex flex-col min-h-screen">
            <Navbar />
            <main className="flex-grow p-5">
                <div className="text-4xl font-bold text-center">
                    <p>REVIEW FORM</p>
                </div>
                <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                    <form onSubmit={submitReview}>
                    <label className="font-bold">Customer Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={reviewDetails.customerId} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Appointment Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointmentId" value={reviewDetails.appointmentId} onChange={handleInputChange} type="text" required />
                        {/* Input fields for comment, rating, etc. Similar to our vehicle form */}
                        <label className="font-bold">Comment</label>
                        <textarea className="w-full p-4 rounded border border-gray-400 mb-5" name="comment" value={reviewDetails.comment}
                            onChange={handleInputChange} required/>

                        <label className="font-bold">Rating</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="rating" type="number" min="1" max="5" value={reviewDetails.rating}
                            onChange={handleInputChange} required/>

                        {/* Add additional fields as necessary. Consider removing mechanicReply from this form. */}
                        
                        <div className="flex justify-center">
                            <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">Submit Review</button>
                        </div>
                    </form>
                </div>
            </main>
            <Footer/>
        </div>
    );
}

export default AddReview;
