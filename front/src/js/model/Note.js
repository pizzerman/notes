import axios from 'axios';

export default class Note {
    constructor() {}

    async getNotes(username) {
        try {
            const res = await axios.get(`http://localhost:8080/api/notes/${username}`);
            this.notes = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async getNotesByDate(username, date) {
        try {
            const res = await axios.post(`http://localhost:8080/api/notes/${username}`, {
                date: date
            });
            this.notes = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async getNotesByTitle(username, title) {
        try {
            const res = await axios.post(`http://localhost:8080/api/notes/${username}`, {
                title: title
            });
            this.notes = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async getNotesByTitleAndDate(username, title, date) {
        try {
            const res = await axios.post(`http://localhost:8080/api/notes/${username}`, {
                date: date,
                title: title
            });
            this.notes = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async getNote(id, censored) {
        try {
            const res = await axios.get(`http://localhost:8080/api/notes/${id}/${censored}`);
            this.note = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async addNote(username, title, text) {
        try {
            const res = await axios.post('http://localhost:8080/api/notes', {
                title: title,
                date: null,
                text: text,
                user: {
                    username: username
                }
            });
            this.note = res.data;
        } catch(error) {
            console.error(error);
            this.error = error.response.data;
        }
    };

    async updateNote(username, id, title, date, text) {
        try {
            const res = await axios.post('http://localhost:8080/api/notes', {
                id: id,
                title: title,
                date: date,
                text: text,
                user: {
                    username: username
                }
            });
            this.note = res.data;
        } catch(error) {
            console.error(error);
            this.error = error.response.data;
        }
    };

    async deleteNote(id) {
        try {
            await axios.delete(`http://localhost:8080/api/notes/${id}`);
        } catch(error) {
            console.error(error);
        }
    };
}