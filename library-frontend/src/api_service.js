import axios from "axios";


export class API{

    static async login(body){
        return await axios.post(`http://localhost:8080/api/auth/signin`, JSON.stringify(body), {
            headers: {
              'Content-Type': 'application/json'
            }
          });
    }

    static async getAllBooks(){
        const token = sessionStorage.getItem('Authorization');
        return await axios.get(`http://localhost:8080/book/all`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }
          });
    }

    static async getAllAuthors(){
        const token = sessionStorage.getItem('Authorization');
        return await axios.get(`http://localhost:8080/author/all`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }
          });
    }

    static async getAllCategories(){
        const token = sessionStorage.getItem('Authorization');
        return await axios.get(`http://localhost:8080/category/all`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }
          });
    }

    static async createAuthor(body){
        const token = sessionStorage.getItem('Authorization');
        return await axios.post(`http://localhost:8080/moderator/author`, JSON.stringify(body), {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }
          });
    }

    static async createBook(body){
        const token = sessionStorage.getItem('Authorization');
        return await axios.post(`http://localhost:8080/moderator/book`, JSON.stringify(body), {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }
          });
    }

    static async deleteBook(id){
        const token = sessionStorage.getItem('Authorization');
        return await axios.delete(`http://localhost:8080/moderator/book/${id}`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }});
    }

    static async rentBook(id, bookItem){
        const token = sessionStorage.getItem('Authorization');
        return await axios.post(`http://localhost:8080/book/${id}/rent_book`, JSON.stringify(bookItem), {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer Bearer ${token}`
            }});
    }

    static async getBookByISBN(ISBN){
      const token = sessionStorage.getItem('Authorization');
      return await axios.get(`http://localhost:8080/book/ISBN/${ISBN}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer Bearer ${token}`
        }});
    }

    static async checkIfBookCanBeCreated(ISBN){
      const token = sessionStorage.getItem('Authorization');
      return await axios.get(`http://localhost:8080/moderator/book/ISBN/${ISBN}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer Bearer ${token}`
        }});
    }

    static async updateBookAllFileds(id, body){
      const token = sessionStorage.getItem('Authorization');
      return await axios.put(`http://localhost:8080/moderator/book/${id}`, JSON.stringify(body), {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer Bearer ${token}`
        }
      })
    }

    static async updateBooItemsForBook(id, body){
      const token = sessionStorage.getItem('Authorization');
      return await axios.put(`http://localhost:8080/moderator/book/${id}/bookItems`, JSON.stringify(body), {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer Bearer ${token}`
        }
      })
    }

}