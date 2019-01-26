import { elements } from './base';

export const clearCensorForm = () => {
    elements.censorForm.innerHTML = '';
    elements.censorAddBtn.children[0].classList.remove('ion-ios-remove-circle');
    elements.censorAddBtn.children[0].classList.add('ion-ios-add-circle');
};

export const getCensorInput = () => document.getElementById('censor-id').value;
export const getAddBtnValue = () => elements.censorAddBtn.children[0].className;
export const getShowBtnValue = () => elements.censorShowBtn.children[0].className;

export const clearCensorInput = () => {
    document.getElementById('censor-id').value = '';
};

export const fillCensorForm = () => {
    const markup = `
        <input type="text" class="col censor-field" id="censor-id" placeholder="Zwrot...">
        <button class="col censor-btn">
            <span>Dodaj</span>
        </button>`;
    elements.censorForm.insertAdjacentHTML('beforeend', markup);
    elements.censorAddBtn.children[0].classList.remove('ion-ios-add-circle');
    elements.censorAddBtn.children[0].classList.add('ion-ios-remove-circle');
};

export const fillCensorFormError = errorMsg => {
    clearCensorForm();
    fillCensorForm();
    const markup = `
        <div class="row">
            <p class="error">${errorMsg}</p>
        </div>`;
    elements.censorForm.insertAdjacentHTML('afterbegin', markup);
};

export const clearList = () => {
    elements.censorList.innerHTML = '';
    elements.censorShowBtn.children[0].classList.remove('ion-ios-arrow-dropup-circle');
    elements.censorShowBtn.children[0].classList.add('ion-ios-arrow-dropdown-circle');
    //elements.censorButtons.innerHTML = '';
};

const renderPhraseForList = phrase => {
    const markup =
        `<li class="censor-item" id="phrase_${phrase.censoredPhrase}"">
            <div class="censor-word">
                <p>${phrase.censoredPhrase}</p>
            </div>
            <div class="censor-delete">
                <button type="button" class="btn-small delete-btn">
                    <i class="ion-ios-close-circle"></i>
                </button>
            </div>
        </li>`;

    elements.censorList.insertAdjacentHTML('beforeend', markup);
};

export const renderPhrases = (phrases/*, page = 1, phrasePerPage = 8*/) => {
    phrases
        /*.slice((page - 1) * phrasePerPage, page * phrasePerPage)*/
        .forEach(renderPhraseForList);

    //renderPageButtons(page, phrases.length, phrasePerPage);
    elements.censorShowBtn.children[0].classList.remove('ion-ios-arrow-dropdown-circle');
    elements.censorShowBtn.children[0].classList.add('ion-ios-arrow-dropup-circle');
};

/*const renderPageButtons = (page, noteNum, notePerPage) => {
    const pagesNum = Math.ceil(noteNum / notePerPage);

    let btn;

    if(page === 1 && pagesNum > 1) {
        btn = `
        <button class="btn-small page-next" data-goToPage=${page + 1}>
            <i class="ion-ios-arrow-dropright-circle"></i>
        </button>`
    } else if(page < pagesNum) {
        btn = `
        <button class="btn-small page-prev" data-goToPage=${page - 1}>
            <i class="ion-ios-arrow-dropleft-circle"></i>
        </button>
        <button class="btn-small page-next" data-goToPage=${page + 1}>
            <i class="ion-ios-arrow-dropright-circle"></i>
        </button>`
    } else if(page === pagesNum && pagesNum > 1) {
        btn = `
        <button class="btn-small page-prev" data-goToPage=${page - 1}>
            <i class="ion-ios-arrow-dropleft-circle"></i>
        </button>`
    }

    if(noteNum > notePerPage) {
        elements.notePageButtons.insertAdjacentHTML('afterbegin', btn);
    }
};*/