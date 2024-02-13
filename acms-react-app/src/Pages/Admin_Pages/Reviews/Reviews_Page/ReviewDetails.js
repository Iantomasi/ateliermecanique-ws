import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import Sidebar from '../../../../Components/Navigation_Bars/Sidebar/Sidebar.js';

function ReviewDetails() {
    const { reviewId } = useParams();
    const navigate = useNavigate();
    const [reviewDetails, setReviewDetails] = useState({
        reviewId: '',
        customerId: '',
        appointmentId: '',
        comment: '',
        rating: 0.0,
        reviewDate: '',
       /* mechanicReply: ''*/

    });

    const [publicContent, setPublicContent] = useState(true); // Define publicContent and its setter
    const [message, setMessage] = useState(''); // Define message and its setter
    const [showConfirmation, setShowConfirmation] = useState(false); // State to manage delete confirmation

    useEffect(() => {
        adminService.getReviewById(reviewId)
            .then(res => {
                if (res.status === 200) {
                    setPublicContent(true);
                    setReviewDetails(res.data);
                }
            })
            .catch(error => {
                setPublicContent(false);
                setMessage(error.response.data);
                console.error('Error fetching review details', error);
            });
    }, [reviewId]);

    function updateReview(event) {
        event.preventDefault();

        adminService.updateReview(reviewId, reviewDetails)
            .then(res => {
                if (res.status === 200) {
                    alert('Review: ' + reviewId + ' has been updated!');
                    navigate(`/admin/reviews`);
                }
            })
            .catch(err => {
                console.error('Error updating review:', err);
            });

    }

    function handleInputChange(event) {
        const { name, value } = event.target;
        setReviewDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    /*
    function handleInputChange(event) {
        const { name, value } = event.target;
        setInvoiceDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    }




    function confirmDelete() {
        setShowConfirmation(true);
    }

    function cancelDelete() {
        setShowConfirmation(false);
    }

    function deleteInvoice() {
        adminService.deleteInvoice(invoiceId)
            .then(res => {
                if (res.status === 204) {
                    alert('Invoice has been deleted!');
                    navigate(`/admin/invoices`);
                }
            })
            .catch(err => {
                console.error('Error deleting invoice:', err);
            });
        setShowConfirmation(false);
    }
    */

    // Render the review details form
    return (
        <div className="flex flex-col min-h-screen">
            {publicContent ? (
                <div>
                    <Navbar />
                    <div className="flex flex-col md:flex-row">
                        <Sidebar />
                        <main className="flex-grow p-5">
                            <div className="text-4xl font-bold text-center">
                                <p>REVIEW DETAILS</p>
                            </div>
                    {/* Rest of the component layout */}
                    <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                    <form className="customervehicle-user-details-form" onSubmit={updateReview}>
                        <label className="font-bold">Customer Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={reviewDetails.customerId} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Appointment Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointmentId" value={reviewDetails.appointmentId} onChange={handleInputChange }type="text" required />

                        <label className="font-bold">Comments</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="comment" value={reviewDetails.comment} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Rating</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="rating" value={reviewDetails.rating} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Date & Time</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="reviewDate" value={reviewDetails.reviewDate} onChange={handleInputChange} type="text" required />

                      
                        <label className="font-bold">Reply</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="reviewId" value={reviewDetails.mechanicReply} type="text" placeholder="No reply"/>
                        <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">
                            Save
                        </button>
                    </form>
                        </div>
                        {/*showConfirmation && (
                                <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-50">
                                    <div className="bg-white p-5 rounded-lg shadow-lg">
                                        <p className="text-lg mb-2">Are you sure you want to delete this invoice?</p>
                                        <div className="flex justify-center space-x-4">
                                            <button className="bg-red-500 text-white px-4 py-2 rounded font-bold" onClick={deleteInvoice}>
                                                Yes
                                            </button>
                                            <button className="bg-gray-300 text-gray-700 px-4 py-2 rounded font-bold" onClick={cancelDelete}>
                                                No
                                            </button>
                                        </div>
                                    </div>
                                </div>
                        )*/}
                        </main>
                    </div>
                </div>
            ) : (
                <div className="flex-1 text-center">
                    {/* Error handling UI */}
                </div>
            )}
            <Footer />
        </div>
    );
}

export default ReviewDetails;
