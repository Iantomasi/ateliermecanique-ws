import React from 'react';
import { Link } from 'react-router-dom';
import {  useNavigate } from 'react-router-dom';
import LanguageSwitcher from '../../Localization/LanguageSwitcher'; 
import { useTranslation } from 'react-i18next'; 

function Navbar() {
  const navigate = useNavigate();
  const { t } = useTranslation(); 

  const navigateToSignup = () => {
    navigate('/signup');
  };
  const handleLogoClick = () => {
    navigate('/');
  };

  return (
    <header className="bg-white">
      <nav className="flex flex-col sm:flex-row justify-between items-center p-4 sm:h-20">
        {/* Left-aligned content */}
        <div className="flex items-center">
          <img src="logo.svg" alt="app logo" className="mr-2" onClick={handleLogoClick} />

          <h1 className="text-4xl font-bold cursor-pointer" onClick={handleLogoClick}>{t('navbar.title')}</h1>
        </div>

        {/* Middle-aligned links */}
        <div className="flex justify-center mt-4 sm:mt-0">
        <div className="mr-4">
            <LanguageSwitcher /> {/* Place LanguageSwitcher here */}
          </div>

          <a href="/about" className="text-gray-700 hover:text-gray-900 px-2 py-1">{t('navbar.about')}</a>
          <a href="/services" className="text-gray-700 hover:text-gray-900 px-2 py-1">{t('navbar.services')}</a>
          <a href="/contact" className="text-gray-700 hover:text-gray-900 px-2 py-1">{t('navbar.contact')}</a>
        </div>

        {/* Right-aligned links */}
        <div className="flex items-center mt-4 sm:mt-0">
          <Link to="/login" className="text-gray-700 hover:text-gray-900 px-2 py-1">{t('navbar.login')}</Link>
          <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold ml-2 transform transition duration-300 hover:scale-110"
            onClick={navigateToSignup}>{t('navbar.signup')}</button>
        </div>
      </nav>
      <div className="w-full h-px bg-black mt-5"></div>
    </header>
  );
}

export default Navbar;