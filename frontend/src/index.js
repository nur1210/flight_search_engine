import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import {AuthProvider} from "./context/AuthProvider";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {SoftUIControllerProvider} from "./context/UIProvider";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <SoftUIControllerProvider>
                    <Routes>
                        <Route path="/*" element={<App/>}/>
                    </Routes>
                </SoftUIControllerProvider>
            </AuthProvider>
        </BrowserRouter>
    </React.StrictMode>
);

