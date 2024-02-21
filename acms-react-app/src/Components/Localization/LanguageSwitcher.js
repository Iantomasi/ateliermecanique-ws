import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; 
import { useTranslation } from 'react-i18next';
import { faGlobe } from '@fortawesome/free-solid-svg-icons';


function LanguageSwitcher() {
    const { i18n } = useTranslation();

    return (
        <div className="language-switcher">
            <FontAwesomeIcon icon={faGlobe} className="icon" />
        <select
            value={i18n.language}
            onChange={(e) => i18n.changeLanguage(e.target.value)}>
            <option value="en">EN</option>
            <option value="fr">FR</option>
        </select>
        </div>
    );
}

export default LanguageSwitcher;
