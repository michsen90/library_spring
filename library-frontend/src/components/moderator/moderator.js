import React, { useEffect, useState } from "react";
import { API } from "../../api_service";
import { Button, Modal, Table } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { faPenToSquare } from "@fortawesome/free-regular-svg-icons";
import CreateAuthorForm from "./create_author_form";
import CreateBookForm from "./create_book_form";

function ModeratorLayout() {
    const [books, setBooks] = useState([]);
    const [squere, setSquere] = useState(-1);
    const [trash, setTrash] = useState(-1);
    const [showDetails, setShowDetails] = useState(false);
    const [showFormNewAuthor, setShowFormNewAuthor] = useState(false);
    const [showFormNewBook, setShowFormNewBook] = useState(false);
    const [createdBook, setCreatedBook] = useState(null);

    useEffect(() => {
        API.getAllBooks()
            .then(response => setBooks(response.data))
            .catch(err => console.log(err));
    }, []);

    useEffect( ()=> {
        if(createdBook !== null){
            books.push(createdBook);
            setCreatedBook(null);
        }
    }, [createdBook, books]);

    const handleShowBookDetails = () => setShowDetails(true);
    const handleCloseBookDetails = () => setShowDetails(false);
    const handleShowFormNewAuthor = () => setShowFormNewAuthor(true);
    const handleCloseFormNewAuthor = () => setShowFormNewAuthor(false);
    const handleShowFormNewBook = () => setShowFormNewBook(true);
    const handleCloseFormNewBook = () => setShowFormNewBook(false);

    const removeBook = id => {
        API.deleteBook(id).then(response => console.log(response)).catch(err => console.log(err));
        const newBooks = books.filter(book => book.id !== id);
        setBooks(newBooks);
    }

    return (
        <React.Fragment>
            <div style={{margin: "1%"}}>
                <Button onClick={handleShowFormNewBook} variant="success">Create new Book</Button> {' '}
                <Button onClick={handleShowFormNewAuthor}>Create new Author</Button>
            </div>
            <Table variant="dark" striped hover bordered>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>ISBN</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {books && books.map(book => {
                        return (
                            <tr key={book.id} >
                                <td onClick={handleShowBookDetails} style={{cursor: "pointer"}}>{book.title}</td>
                                <td>{book.isbn}</td>
                                <td>
                                    {book && book.authors.map(author => {
                                        return (
                                            <p key={author.id}>{author.firstname} {author.lastname}</p>
                                        )
                                    })}
                                </td>
                                <td>
                                    {book && book.categories.map(category => {
                                        return (
                                            <p key={category.id}>{category.bookType}</p>
                                        )
                                    })}
                                </td>
                                <td>
                                    <FontAwesomeIcon
                                        icon={faPenToSquare} size="lg"
                                        style={{ color: squere === book.id ? "yellow" : "white" }}
                                        onMouseEnter={() => setSquere(book.id)}
                                        onMouseLeave={() => setSquere(-1)}
                                        beat={squere === book.id ? true : false}
                                    />
                                    <FontAwesomeIcon
                                        icon={faTrash} style={{ marginLeft: "10px", color: trash === book.id ? "red" : "white" }}
                                        onMouseEnter={() => setTrash(book.id)}
                                        onMouseLeave={() => setTrash(-1)}
                                        beat={trash === book.id ? true : false}
                                        onClick={() => removeBook(book.id)}
                                    />
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
            <Modal show={showDetails} onHide={handleCloseBookDetails} centered >
                <Modal.Header>
                    <Modal.Title>Book details:</Modal.Title>
                </Modal.Header>
                <Modal.Body>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="info" onClick={handleCloseBookDetails}>Close</Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showFormNewAuthor} onHide={handleCloseFormNewAuthor} centered>
                <Modal.Header>
                    <Modal.Title>New Author:</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreateAuthorForm handleCloseFormNewAuthor={handleCloseFormNewAuthor}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="info" onClick={handleCloseFormNewAuthor}>Close</Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showFormNewBook} onHide={handleCloseFormNewBook} centered>
                <Modal.Header>
                    <Modal.Title>New Book:</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreateBookForm handleCloseFormNewBook={handleCloseFormNewBook} setCreatedBook={setCreatedBook}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="info" onClick={handleCloseFormNewBook}>Close</Button>
                </Modal.Footer>
            </Modal>
        </React.Fragment>
    );
}

export default ModeratorLayout;