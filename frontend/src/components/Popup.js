import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import PriceAlertService from "../services/PriceAlertService";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

function Popup(props) {
    const params = props.props;
    const [show, setShow] = useState(false);
    const axiosPrivate = useAxiosPrivate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleSubmit = async () => {
        console.log(params);
        try {
            const response = await axiosPrivate.post(`/alerts`,(params));
            if(isNaN(response.data)){
                console.log("Success");
                setShow(false);
                return;
            }
            console.log("Error");
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <>
            <Button variant="outline-dark" onClick={handleShow}>
                Set price alert
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create a new Price Alert</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Ticket prices change all the time. We can’t prevent it, but we can let you know.</p>
                    <p>Trip details</p>
                    <p>Fly from {params.origin} to {params.destination}</p>
                    <p>From {params.departureDate} to {params.returnDate}</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-dark" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="dark" onClick={handleSubmit}>
                        Notify Me
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default Popup;