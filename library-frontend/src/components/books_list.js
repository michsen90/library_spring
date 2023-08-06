import React, { useEffect, useState } from "react";
import { API } from "../api_service";
import { Button, Col, Form, FormControl, InputGroup, ListGroup, Modal, Row, Table } from "react-bootstrap";
import RentBook from "./rent_book";

function BooksList() {

    const [books, setBooks] = useState([]);
    const [booksToShow, setBooksToShow] = useState([]);
    const [word, setWord] = useState('');
    const [selectedBook, setSelectedBook] = useState(null);
    const [showRentForm, setShowRentForm] = useState(false);

    const updateBooks = () => {
        API.getAllBooks()
            .then(response => {
                setBooks(response.data);
                setBooksToShow(response.data);
            })
            .catch(err => console.log(err));
    }

    useEffect(() => {
        updateBooks();
    }, []);

    useEffect(() => {
        setBooksToShow(books.filter(book => book.title.includes(word)));
    }, [word, books]);

    const handleShowRentForm = () => setShowRentForm(true);
    const handleCloseRentForm = () => setShowRentForm(false);

    const selectBook = book => evt => {
        setSelectedBook(book);
    }

    const reloadBooksList = () => {
        updateBooks();
    }

    return (
        <React.Fragment>
            <Row md={2}>
                <Col xs={4}>
                    <Form style={{ marginTop: "20px" }}>
                        <FormControl type="text" placeholder="Search by title..." value={word} onChange={evt => setWord(evt.target.value)} ></FormControl>
                    </Form>

                    <ListGroup style={{ marginTop: "10px" }}>
                        {booksToShow && booksToShow.map(book => {
                            return (
                                <ListGroup.Item action key={book.id} variant="dark" onClick={selectBook(book)} >{book.title}</ListGroup.Item>
                            )
                        })
                        }
                    </ListGroup>
                </Col>
                <Col xs={8} style={{ marginTop: "20px" }}>
                    {selectedBook ?
                        <React.Fragment>
                            <Table variant="dark" responsive hover bordered striped style={{ width: "99%" }}>
                                <thead>
                                    <tr>
                                        <th>Title</th>
                                        <th>ISBN</th>
                                        <th>Author</th>
                                        <th>Category</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr key={selectedBook.id}>
                                        <td>{selectedBook.title}</td>
                                        <td>{selectedBook.isbn}</td>
                                        <td>
                                            {selectedBook && selectedBook.authors.map(author => {
                                                return (
                                                    <p key={author.id}>{author.firstname} {author.lastname}</p>
                                                )
                                            })}
                                        </td>
                                        <td>
                                            {selectedBook && selectedBook.categories.map(category => {
                                                return (
                                                    <p key={category.id}>{category.bookType}</p>
                                                )
                                            })}
                                        </td>
                                    </tr>
                                </tbody>
                            </Table>
                            <Row md={3}>
                                <Col></Col>
                                <Col>
                                    {selectedBook.bookItems.map(bookItem => {
                                        return (
                                            <InputGroup className="mb-3" style={{ width: "100%" }}>
                                                <Form.Control
                                                    value={bookItem.available ? "Available" : "Rented"}
                                                    disabled
                                                    style={{ backgroundColor: bookItem.available ? "green" : "red", width: "50%"}}
                                                />
                                                <Button
                                                    variant={bookItem.available ? "outline-success" : "outline-danger"}
                                                    disabled={!bookItem.available} style={{ width: "50%", cursor: bookItem.available === true ? "pointer" : "cursor-na" }}
                                                    onClick={handleShowRentForm}
                                                >
                                                    Rent book ID: {bookItem.id}
                                                </Button>
                                            </InputGroup>
                                        )
                                    })}

                                </Col>
                                <Col></Col>
                            </Row>

                        </React.Fragment>
                        : null}
                </Col>
            </Row>
            <Modal show={showRentForm} onHide={handleCloseRentForm} centered size="lg">
                <Modal.Header>
                    <Modal.Title>Rent a book:</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <RentBook handleCloseRentForm={handleCloseRentForm} book={selectedBook} reloadBooksList={reloadBooksList} />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="info" onClick={handleCloseRentForm}>Close</Button>
                </Modal.Footer>
            </Modal>
        </React.Fragment>
    );
}

export default BooksList;