import { elements } from './base';

export const clearLogout = () => {
    elements.userLogout.innerHTML = '';
};

export const fillLogout = username => {
    const markup = `
        <p class="col username">${username}</p>
        <button type="button" class="col btn-big logout-btn">
            <i class="ion-ios-log-out"></i>
        </button>`;
    
    elements.userLogout.insertAdjacentHTML('beforeend', markup);
};

export const clearRegisterForm = () => {
    elements.userRegisterForm.innerHTML = '';
};

export const getUsernameFromForm = () => document.getElementById('nick-id').value;
export const getPasswordFromForm = () => document.getElementById('password-id').value;

export const clearFormFields = () => {
    document.getElementById('nick-id').value = '';
    document.getElementById('password-id').value = '';
};

export const fillRegisterView = () => {
    let markup = `
        <button type="button" class="btn-small user-login">
            <i class="ion-ios-person"></i>
        </button>`;
    elements.noteButtons.innerHTML = '';
    elements.noteButtons.insertAdjacentHTML('beforeend', markup);

    markup = `
        <div class="row form-row">
            <div class="col form-label">
                <label>Nazwa</label>
            </div>
            <div class="col form-field">
                <input type="text" id="nick-id" placeholder="Nazwa...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>Hasło</label>
            </div>
            <div class="col form-field">
                <input type="password" id="password-id" placeholder="Hasło...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>&nbsp;</label>
            </div>
            <div class="col form-field">
                <button class="btn login-btn">
                    <span>Zarejestruj</span>
                </button>
            </div>
        </div>`;
    elements.userRegisterForm.insertAdjacentHTML('beforeend', markup);
};

export const fillRegisterViewError = (errorMsg) => {
    clearRegisterForm();
    fillRegisterView();
    const markup = `
        <div class="row">
            <p class="error">${errorMsg}</p>
        </div>`;
    elements.userRegisterForm.insertAdjacentHTML('afterbegin', markup);
};

export const clearLoginForm = () => {
    elements.userLoginForm.innerHTML = '';
};

export const fillLoginView = () => {
    let markup = `
        <button type="button" class="btn-small user-add">
            <i class="ion-ios-person-add"></i>
        </button>`;
    elements.noteButtons.innerHTML = '';
    elements.noteButtons.insertAdjacentHTML('beforeend', markup);

    markup = `
        <div class="row form-row">
            <div class="col form-label">
                <label>Nazwa</label>
            </div>
            <div class="col form-field">
                <input type="text" id="nick-id" placeholder="Nazwa...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>Hasło</label>
            </div>
            <div class="col form-field">
                <input type="password" id="password-id" placeholder="Hasło...">
            </div>
        </div>
        <div class="row form-row">
            <div class="col form-label">
                <label>&nbsp;</label>
            </div>
            <div class="col form-field">
                <button class="btn login-btn">
                    <span>Zaloguj</span>
                </button>
            </div>
        </div>`;
    elements.userLoginForm.insertAdjacentHTML('beforeend', markup);
};

export const fillLoginViewError = (errorMsg) => {
    clearLoginForm();
    fillLoginView();
    const markup = `
        <div class="row">
            <p class="error">${errorMsg}</p>
        </div>`;
    elements.userLoginForm.insertAdjacentHTML('afterbegin', markup);
};