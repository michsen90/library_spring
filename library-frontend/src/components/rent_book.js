import React, { useState } from "react";
import { Button, Col, Form, Row, Table } from "react-bootstrap";
import { API } from "../api_service";

function RentBook({ handleCloseRentForm, book, bookItem, updateBooks }) {

    const [validated, setValidated] = useState(false);
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const handleSubmit = evt => {
        evt.preventDefault();
        const form = evt.currentTarget;
        if (form.checkValidity() === false) {
            //todo
        }
        setValidated(true);
        API.rentBook(bookItem.id, {startDate: startDate, endDate: endDate, userId: sessionStorage.getItem("userId")})
            .then(response => {
                console.log(response);
                updateBooks();
            })
            .catch(err => console.log(err));
        handleCloseRentForm();
    }

    return (
        <React.Fragment>
            <Table variant="dark" bordered striped hover>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>ISBN</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{book.title}</td>
                        <td>{book.isbn}</td>
                    </tr>
                </tbody>
            </Table>
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
                <Row md={2}>
                    <Col>
                        <Form.Group>
                            <Form.Label>Start date</Form.Label>
                            <Form.Control required type="date" value={startDate} onChange={evt => setStartDate(evt.target.value)} />
                            <Form.Control.Feedback type="invalid">Provide correct data</Form.Control.Feedback>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Form.Label>End date</Form.Label>
                            <Form.Control required type="date" value={endDate} onChange={evt => setEndDate(evt.target.value)} />
                        </Form.Group>
                    </Col>
                </Row>
                <Button type="submit" >Rent book</Button>
            </Form>
        </React.Fragment>
    );
}

export default RentBook;