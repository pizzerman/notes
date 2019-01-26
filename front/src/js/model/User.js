import axios from 'axios';

export default class User {
    constructor() {}

    async loginUser(username, password) {
        try {
            const res = await axios.post('http://localhost:8080/api/users', {
                username: username,
                password: password,
                enabled: 1
            });
            this.user = res.data;
        } catch(error) {
            console.error(error);
            this.error = error.response.data;
        }
    };

    async addUser(username, password) {
        try {
            const res = await axios.post('http://localhost:8080/api/register', {
                username: username,
                password: password,
                enabled: 1,
                authorities: [{
                    primaryKey: {
                        user: {
                            username: username
                        },
                        authority: "ROLE_USER"
                }
                }]
            });
            this.user = res.data;
        } catch(error) {
            console.error(error);
            this.error = error.response.data;
        }
    };
}