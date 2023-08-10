import React, { useEffect, useState } from "react";
import { API } from "../api_service";
import { Button, Col, Form, FormControl, ListGroup, Modal, OverlayTrigger, Row, Table, Tooltip } from "react-bootstrap";
import RentBook from "./rent_book";

function BooksList() {

    const [books, setBooks] = useState([]);
    const [booksToShow, setBooksToShow] = useState([]);
    const [word, setWord] = useState('');
    const [selectedBook, setSelectedBook] = useState(null);
    const [showRentForm, setShowRentForm] = useState(false);
    const [selectedBookItem, setSelectedBookItem] = useState(null);

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

    console.log(books);

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
                            <Row>
                                <Table>

                                </Table>
                            </Row>
                            <Row>
                                <Col>
                                    <Table variant="light" hover bordered striped>
                                        <thead>
                                            <tr>
                                                <th>Status</th>
                                                <th>Book item ID</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {selectedBook.bookItems.map(bookItem => {
                                                return (
                                                    <React.Fragment>
                                                        <OverlayTrigger key={bookItem.id} placement="auto" overlay={
                                                            <Tooltip id="overlay-top">
                                                                Expected available date:
                                                            </Tooltip>
                                                        }
                                                        >
                                                            <tr key={bookItem.id}>
                                                                <td style={{ color: bookItem.available ? "darkgreen" : "red" }}>{bookItem.available ? "Available" : "Rented"}</td>
                                                                <td style={{ color: bookItem.available ? "darkgreen" : "red" }}>ID: {bookItem.id}</td>
                                                                <td>
                                                                    <Button
                                                                        variant={bookItem.available ? "outline-success" : "outline-danger"}
                                                                        disabled={!bookItem.available} style={{ width: "50%", cursor: bookItem.available === true ? "pointer" : "cursor-na" }}
                                                                        onClick={() => {
                                                                            setSelectedBookItem(bookItem);
                                                                            handleShowRentForm();
                                                                        }}
                                                                    >
                                                                        Rent book
                                                                    </Button>
                                                                </td>
                                                            </tr>
                                                        </OverlayTrigger>
                                                    </React.Fragment>
                                                )
                                            })}
                                        </tbody>
                                    </Table>
                                </Col>
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
                    <RentBook handleCloseRentForm={handleCloseRentForm} book={selectedBook} bookItem={selectedBookItem} updateBooks={updateBooks} />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="info" onClick={handleCloseRentForm}>Close</Button>
                </Modal.Footer>
            </Modal>
        </React.Fragment>
    );
}

export default BooksList;