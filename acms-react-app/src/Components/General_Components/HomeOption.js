import React from 'react';
import './HomeOption.css';
import { useNavigate } from 'react-router-dom';

const HomeOption = ({src,label}) => {
  
  const navigate = useNavigate();
  function navigateToNext(){
    navigate("/admin/"+label);
  }
  return (
    <div className="HomeOption" onClick={navigateToNext}>
        <h2>{label}</h2>
        <img src={src}/>
    </div>
  );
};

export default HomeOption;