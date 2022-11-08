import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function Popup(props) {
    const params = props.props;
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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
                    <Button variant="dark" onClick={handleClose}>
                        Notify Me
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default Popup;