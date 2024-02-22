import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import authService from '../../../../Services/auth.service.js';
import Footer from '../../../../Components/Footer/Footer.js';
import UserDisplay from '../../../../Components/User_Components/UserDisplay.js';
import MechanicDisplay from '../../../../Components/User_Components/MechanicDisplay.js';
import CustomDateTimePicker from '../../../../Components/DateTimePicker/CustomDateTimePicker.js';


function ReviewDetails() {
    const { reviewId } = useParams();
    const { customerId } = useParams();
    const navigate = useNavigate();
    const [reviewDetails, setReviewDetails] = useState({
        reviewId: '',
        customerId :'',
        appointmentId: '',
        comment: '',
        rating: 0.0,
        reviewDate: '',
        mechanicReply: ''
    });

    const [userRole, setUserRole] = useState('');
    const [publicContent, setPublicContent] = useState(true);
    const [message, setMessage] = useState('');
    const [showConfirmation, setShowConfirmation] = useState(false);

    useEffect(() => {
        const currentUser = authService.getCurrentUser();
        if (currentUser && currentUser.roles) {
            if (currentUser.roles.includes('ROLE_ADMIN')) {
                setUserRole('admin');
            } else if (currentUser.roles.includes('ROLE_CUSTOMER')) {
                setUserRole('user');
            }
        }

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

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setReviewDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const updateReview = (event) => {
        event.preventDefault();
        adminService.updateReview(reviewId, reviewDetails)
            .then(res => {
                if (res.status === 200) {
                    alert('Review: ' + reviewId + ' has been updated!');
                    navigate(-1);
                }
            })
            .catch(err => {
                console.error('Error updating review:', err);
            });
    };

    const deleteReview = () => {
        adminService.deleteReview(reviewId)
            .then(res => {
                if (res.status === 204) {
                    alert('Review has been deleted!');
                    navigate(-1);                }
            })
            .catch(err => {
                alert('You can only delete your own reviews.');
            });
        setShowConfirmation(false);
    };

    const confirmDelete = () => setShowConfirmation(true);
    const cancelDelete = () => setShowConfirmation(false);

    return (
        <div className="flex flex-col min-h-screen">
            {publicContent ? (
                <div>
                    <Navbar />
                    <div className="flex">
                    <div className="ml-5 mt-1">
                        {userRole === 'admin' ? <MechanicDisplay /> : <UserDisplay />}
                           </div>
                        <main className="flex-grow p-5">
                            <div className="text-4xl font-bold text-center">
                                <p>REVIEW DETAILS</p>
                            </div>
                            <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                                {userRole === 'admin' ? (
                                    <form className="customervehicle-user-details-form" onSubmit={updateReview}>
                                        {/* Admin-specific fields */}
                                        <label className="font-bold">Customer Id</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={reviewDetails.customerId}/>

                                        <label className="font-bold">Appointment Id</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointmentId" value={reviewDetails.appointmentId}/>

                                        <label className="font-bold">Comments</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="comment" value={reviewDetails.comment}/>

                                        <label className="font-bold">Rating</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="rating" value={reviewDetails.rating}/>

                                        <label className="font-bold">Date & Time</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="reviewDate" value={reviewDetails.reviewDate}/>
                                        <label className="font-bold">Reply</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mechanicReply" value={reviewDetails.mechanicReply} type="text" onChange={handleInputChange} placeholder="No reply"/>

                                        <div className="flex justify-center space-x-10">
                                            <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">
                                                Save
                                            </button>
                                            <button className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" onClick={confirmDelete} type="button">
                                                Delete
                                            </button>
                                        </div>
                                    </form>
                                ) : (
                                    <form className="customervehicle-user-details-form" onSubmit={updateReview}>
                                        {/* User-specific fields */}
                                        <label className="font-bold">Customer Id</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={reviewDetails.customerId} onChange={handleInputChange} type="text" required />

                                        <label className="font-bold">Appointment Id</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointmentId" value={reviewDetails.appointmentId} onChange={handleInputChange} type="text" required />

                                        <label className="font-bold">Comments</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="comment" value={reviewDetails.comment} onChange={handleInputChange} type="text" required />

                                        <label className="font-bold">Rating</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="rating" value={reviewDetails.rating} onChange={handleInputChange} type="text" required />

                                        <label className="font-bold">Date & Time</label>
                                        <CustomDateTimePicker
                                        value={reviewDetails.reviewDate}
                                        onChange={(date) => setReviewDetails({ ...reviewDetails, reviewDate: date })}
                                        />
                                        
                                        <label className="font-bold">Reply</label>
                                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mechanicReply" value={reviewDetails.mechanicReply} type="text" placeholder="No reply"/>

                                        <div className="flex justify-center space-x-10">
                                            <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">
                                                Save
                                            </button>
                                            <button className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" onClick={confirmDelete} type="button">
                                                Delete
                                            </button>
                                        </div>
                                    </form>
                                )}
                            </div>
                            {showConfirmation && (
                                <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-50">
                                    <div className="bg-white p-5 rounded-lg shadow-lg">
                                        <p className="text-lg mb-2">Are you sure you want to delete this review?</p>
                                        <div className="flex justify-center space-x-4">
                                            <button className="bg-red-500 text-white px-4 py-2 rounded font-bold" onClick={deleteReview}>
                                                Yes
                                            </button>
                                            <button className="bg-gray-300 text-gray-700 px-4 py-2 rounded font-bold" onClick={cancelDelete}>
                                                No
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            )}
                        </main>
                    </div>
                </div>
            ) : (
                <div className="flex-1 text-center">
                    {message}
                </div>
            )}
            <Footer />
        </div>
    );
}

export default ReviewDetails;
