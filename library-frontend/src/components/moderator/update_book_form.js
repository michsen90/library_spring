import React, { useEffect, useState } from "react";
import { API } from "../../api_service";
import { Accordion, Button, Col, Form, Row } from "react-bootstrap";
import AccordionBody from "react-bootstrap/esm/AccordionBody";

function UpdateBookForm({ handleCloseFormUpdateBook, bookToUpdate, setUpdatedBook }) {

    const [validated, setValidated] = useState(false);
    const [title, setTitle] = useState(bookToUpdate.title);
    const [ISBN, setISBN] = useState(bookToUpdate.isbn);
    const [selectedAuthors, setSelectedAuthors] = useState(bookToUpdate.authors);
    const [selectedCategories, setSelectedCategories] = useState(bookToUpdate.categories);
    const [handleAddBookItem, setHandleAddBookItem] = useState(false);
    const [authors, setAuthors] = useState([]);
    const [categories, setCategories] = useState([]);
    const [numberNewBook, setNumberNewBook] = useState(-1);

    useEffect(() => {
        API.getAllAuthors().then(response => setAuthors(response.data)).catch(err => console.log(err));
    }, []);

    useEffect(() => {
        API.getAllCategories().then(response => setCategories(response.data)).catch(err => console.log(err));
    }, []);

    const handleSubmit = evt => {
        evt.preventDefault();
        setValidated(true);
        API.updateBookAllFileds(bookToUpdate.id, 
            {id: bookToUpdate.id, title: title, isbn: ISBN, authors: selectedAuthors, categories: selectedCategories, bookItems: bookToUpdate.bookItems, numberOfNewBook: numberNewBook})
            .then(response => {
                setUpdatedBook(response.data);
                handleCloseFormUpdateBook();
            })
            .catch(err => console.log(err));
    }

    const addAuthor = evt => {
        const value = evt.target.value;
        const isChecked = evt.target.checked;
        var updateAuthors = [...selectedAuthors];
        if (isChecked) {
            if (selectedAuthors.filter(author => author.id === value.id) > 0) {

            } else {
                updateAuthors = ([...selectedAuthors, JSON.parse(value)]);
            }
        } else {
            updateAuthors.splice(updateAuthors.indexOf(value), 1);
        }

        setSelectedAuthors(updateAuthors);
    }

    const addCategory = evt => {
        const value = evt.target.value;
        const isChecked = evt.target.checked;
        var updateCategories = [...selectedCategories];
        if (isChecked) {
            if (selectedCategories.filter(category => category.id === value.id) > 0) {

            } else {
                updateCategories = ([...selectedCategories, JSON.parse(value)]);
            }
        } else {
            updateCategories.splice(updateCategories.indexOf(value), 1);
        }

        setSelectedCategories(updateCategories);
    }

    const authorExist = id => {
        return selectedAuthors.some(function (author) {
            return author.id === id;
        });
    }

    const categoryExist = id => {
        return selectedCategories.some(function (category) {
            return category.id === id;
        });
    }

    return (
        <React.Fragment>
            <Form noValidate validated={validated} onSubmit={handleSubmit} style={{ textAlign: "center" }}>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control required type="text" placeholder="title" value={title} onChange={evt => setTitle(evt.target.value)} />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationISBN">
                        <Form.Label>ISBN</Form.Label>
                        <Form.Control required type="text" placeholder="ISBN" value={ISBN} onChange={evt => setISBN(evt.target.value)} />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Accordion>
                        <Accordion.Item eventKey="authors">
                            <Accordion.Header>Authors</Accordion.Header>
                            <Accordion.Body>
                                {authors && authors.map(author => {
                                    return (
                                        <Form.Check type="checkbox" key={author.id} value={JSON.stringify(author)}
                                            onChange={evt => addAuthor(evt)} label={`${author.firstname} ${author.lastname}`}
                                            checked={authorExist(author.id)}
                                        />
                                    )
                                })}
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                </Row>
                <Row className="mb-3">
                    <Accordion>
                        <Accordion.Item eventKey="categories">
                            <Accordion.Header>Categories</Accordion.Header>
                            <Accordion.Body>
                                {categories && categories.map(category => {
                                    return (
                                        <Form.Check type="checkbox" key={category.id} value={JSON.stringify(category)}
                                            onChange={evt => addCategory(evt)} label={`${category.bookType}`}
                                            checked={categoryExist(category.id)}
                                        />
                                    )
                                })}
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                </Row>
                <Row className="mb-3">
                    <Accordion>
                        <Accordion.Item eventKey="bookItems">
                            <Accordion.Header>Book Items</Accordion.Header>
                            <AccordionBody>
                                <h6>Enter if you want to update number of books!</h6>
                                <Button variant="outline-success" onClick={() => setHandleAddBookItem(!handleAddBookItem)}>Add book item/s</Button>
                                {handleAddBookItem ?
                                    <Form.Group as={Col} controlId="validationNumberOfBook" style={{marginTop: "10px"}}>
                                        <Form.Label>Number of books</Form.Label>
                                        <Form.Control required type="text" placeholder="Enter number of book" value={numberNewBook} onChange={evt => setNumberNewBook(evt.target.value)} />
                                    </Form.Group>
                                    : null}
                            </AccordionBody>
                        </Accordion.Item>
                    </Accordion>
                </Row>
                <Button type="submit">Update book</Button>
            </Form>
        </React.Fragment>
    );
}

export default UpdateBookForm;