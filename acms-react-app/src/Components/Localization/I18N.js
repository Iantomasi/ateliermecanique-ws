import i18n from 'i18next'
import { initReactI18next } from 'react-i18next'
import HttpBackend from 'i18next-http-backend'
import LanguageDetector from 'i18next-browser-languagedetector'

i18n
    .use(HttpBackend)
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        fallbackLng: 'en',
        debug: true,
        lng: 'en',
        backend: {
            loadPath: '/Locals/{{lng}}/translation.json',

            // path to post missing resources
            addPath: '/Locals/{{lng}}/translation.json'
          },
        interpolation: {
            escapeValue: false,
        },
        react: {
            useSuspense: false,
          }
    })

export default i18n


