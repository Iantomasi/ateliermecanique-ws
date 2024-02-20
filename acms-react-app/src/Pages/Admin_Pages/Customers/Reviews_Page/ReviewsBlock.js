import React, {useState} from 'react';
import {useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service';

const ReviewBlock = ({ review, refreshReviews }) => {


    const navigate = useNavigate();

    const handleRowClick = () => {
        navigate(`/admin/reviews/${review.reviewId}`);
    };


    return (
        <>
            <tr className='hover:bg-gray-200 hover:cursor-pointer h-10' onClick={handleRowClick}>
                <td className='hover:cursor-pointer'> {review.reviewId}</td>
                <td className='hover:cursor-pointer'>{review.customerId}</td>
                <td>{review.appointmentId}</td>
                <td>{review.comment}</td>
                <td>{review.rating}</td>
                <td>{review.reviewDate}</td>
            </tr>
        </>
    );
};

export default ReviewBlock;