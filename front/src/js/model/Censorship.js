import axios from 'axios';

export default class Censorship {
    constructor() {}

    async getCensorship(username) {
        try {
            const res = await axios.get(`http://localhost:8080/api/censorship/${username}`);
            this.censors = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async getCensorshipByPhrase(username, phrase) {
        try {
            const res = await axios.get(`http://localhost:8080/api/censorship/${username}/${phrase}`);
            this.censor = res.data;
        } catch(error) {
            console.error(error);
        }
    };

    async addCensorship(username, phrase) {
        try {
            const res = await axios.post(`http://localhost:8080/api/censorship/${username}`, {
                censoredPhrase: phrase
            });
            this.censor = res.data;
        } catch(error) {
            console.error(error);
            this.error = error.response.data;
        }
    };

    async deleteCensorship(username, phrase) {
        try {
            await axios.delete(`http://localhost:8080/api/censorship/${username}/${phrase}`);
        } catch(error) {
            console.error(error);
        }
    };
}