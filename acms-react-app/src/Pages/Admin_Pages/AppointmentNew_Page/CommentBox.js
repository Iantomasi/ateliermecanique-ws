import React, { useState } from 'react';

function CommentBox({ setComments }) {
    return (
        <div className="p-4">
            <label htmlFor="comments" className="block text-sm font-medium text-gray-700">Other</label>
            <textarea
                id="comments"
                name="comments"
                rows="3"
                className="shadow-sm mt-1 block w-full sm:text-sm border border-gray-300 rounded-md p-2"
                placeholder="Comments..."
                onChange={(e) => setComments(e.target.value)}
            />
        </div>
    );
}


export default CommentBox;
