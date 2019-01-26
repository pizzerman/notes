import Note from './model/Note';
import User from './model/User';
import Censorship from './model/Censorship';
import * as noteView from './view/noteView';
import * as userView from './view/userView';
import * as censorshipView from './view/censorshipView';
import { elements } from './view/base';

const state = {};

/**
 * BUTTONS
 */
elements.noteButtons.addEventListener('click', async e => {
    const btnRegister = e.target.closest('.user-add');
    const btnLogin = e.target.closest('.user-login');
    const btnAdd = e.target.closest('.note-add');
    const btnEdit = e.target.closest('.note-edit');   
    const btnDelete = e.target.closest('.note-delete');
    
    if(btnRegister) {
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();
        
        userView.fillRegisterView();
    }

    if(btnLogin) {
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();
        
        userView.fillLoginView();
    }

    if(btnAdd) {
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();

        noteView.unmarkSelected();        
        noteView.fillAddNoteView();
    }

    if(btnEdit && state.note && state.note.note) {
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();

        noteView.fillEditNoteView();

        await state.note.getNote(state.note.note.id, false);
        noteView.fillNoteForm(
            state.note.note.title,
            state.note.note.date,
            state.note.note.text
        );
    }

    //delete note
    if(btnDelete && state.note && state.note.note) {
        noteView.unmarkSelected();

        await state.note.deleteNote(state.note.note.id);
        
        getNotes();
    }
});

/**
 * USER
 */
//register
elements.userRegisterForm.addEventListener('submit', async e => {
    e.preventDefault();

    state.user = new User();

    const username = userView.getUsernameFromForm();
    const password = userView.getPasswordFromForm();
    userView.clearFormFields();

    await state.user.addUser(username, password);

    if(!state.user.error) {
        userView.clearLogout();
        userView.fillLogout(state.user.user.username);
        getNotes();
    } else {
        const errMessage = state.user.error.message;
        state.user.error = null;
        userView.fillRegisterViewError(errMessage);
    }
});

//logout
elements.userLogout.addEventListener('click', e => {
    const btn = e.target.closest('.logout-btn');

    if(btn) {
        state.note = null;
        state.user = null;

        userView.clearLogout();
        
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();
        userView.fillLoginView();

        noteView.clearList();
    }
});

//login
elements.userLoginForm.addEventListener('submit', async e => {
    e.preventDefault();

    state.user = new User();

    const username = userView.getUsernameFromForm();
    const password = userView.getPasswordFromForm();
    userView.clearFormFields();

    await state.user.loginUser(username, password);

    if(!state.user.error) {
        userView.clearLogout();
        userView.fillLogout(state.user.user.username);
        getNotes();
    } else {
        const errMessage = state.user.error.message;
        state.user.error = null;
        userView.fillLoginViewError(errMessage);
    }
});

/**
 * NOTES
 */
const getNotes = async () => {
    state.note = new Note();

    await state.note.getNotes(state.user.user.username);

    noteView.clearList();
    noteView.renderNotes(state.note.notes);

    userView.clearRegisterForm();
    userView.clearLoginForm();
    noteView.clearNoteForm();
    noteView.clearNoteView();
    noteView.fillAddNoteView();
};

//notes list buttons
elements.notePageButtons.addEventListener('click', e => {
    const btn = e.target.closest('.btn-small');

    if(btn) {
        const goToPage = parseInt(btn.dataset.gotopage, 10);
        noteView.clearList();
        noteView.renderNotes(state.note.notes, goToPage);
    }
});

//show note
elements.noteList.addEventListener('click', async e => {
    const el = e.target.closest('.note-link');

    if(el && state.note) {
        const id = parseInt(el.id.split('_')[1], 10);
    
        await state.note.getNote(id, true);

        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();
        noteView.fillShowNoteView(state.note.note);

        noteView.markSelected(id);
    }
});

const addNote = async () => {
    const title = noteView.getTitleFromForm();
    const text = noteView.getTextFromForm();
    noteView.clearFormFields();

    if(state.note) {
        await state.note.addNote(state.user.user.username, title, text);
        
        if(!state.note.error) {
            await state.note.getNotes(state.user.user.username);
            await state.note.getNote(state.note.note.id, true);

            noteView.clearList();
            noteView.renderNotes(state.note.notes);

            userView.clearRegisterForm();
            userView.clearLoginForm();
            noteView.clearNoteForm();
            noteView.clearNoteView();
            noteView.fillShowNoteView(state.note.note);

            noteView.markSelected(state.note.note.id);
        } else {
            const errMessage = state.note.error.message;
            const errors = errMessage.split(';');
            state.note.error = null;
            noteView.fillAddNoteViewError(errors);
        }
    }
};

const updateNote = async () => {
    const title = noteView.getTitleFromForm();
    const date = noteView.getDateFromForm();
    const text = noteView.getTextFromForm();
    noteView.clearFormFields();

    if(state.note && state.note.note) {
        await state.note.updateNote(
            state.user.user.username,
            state.note.note.id,
            title, date, text);

        if(!state.note.error) {
            await state.note.getNotes(state.user.user.username);
            await state.note.getNote(state.note.note.id, true);
            noteView.clearList();
            noteView.renderNotes(state.note.notes);

            userView.clearRegisterForm();
            userView.clearLoginForm();
            noteView.clearNoteForm();
            noteView.clearNoteView();
            noteView.fillShowNoteView(state.note.note);

            noteView.markSelected(state.note.note.id);
        } else {
            const errMessage = state.note.error.message;
            const errors = errMessage.split(';');
            state.note.error = null;
            noteView.fillEditNoteViewError(errors);
            noteView.fillNoteForm(
                state.note.note.title,
                state.note.note.date,
                state.note.note.text
            );
        }
    }
};

//add/update note
elements.noteForm.addEventListener('submit', async e => {
    e.preventDefault();

    if(noteView.getAddUpdateValue() === 'Zapisz') {
        await updateNote();
    } else {
        await addNote();
    }
});

//search notes by title/date
elements.searchFrom.addEventListener('submit', async e => {
    e.preventDefault();
    const title = noteView.getSearch();
    const date = noteView.getDateSearch();

    if(state.note) {
        noteView.clearList();
        noteView.clearSearch();

        if(title && date) {
            const d = date.replace(/-/g, '');
            const titleArr = title.split(' ');
            const processedTitle = titleArr.join();
            
            await state.note.getNotesByTitleAndDate(
                state.user.user.username,
                processedTitle,
                d);

        } else if(title && !date) {
            const titleArr = title.split(' ');
            const processedTitle = titleArr.join();
            
            await state.note.getNotesByTitle(
                state.user.user.username,
                processedTitle);

        } else if(!title && date) {
            const d = date.replace(/-/g, '');
            await state.note.getNotesByDate(state.user.user.username, d);
        } else {
            await state.note.getNotes(state.user.user.username);
        }

        noteView.renderNotes(state.note.notes);
                    
        userView.clearRegisterForm();
        userView.clearLoginForm();
        noteView.clearNoteForm();
        noteView.clearNoteView();
        noteView.fillAddNoteView();
    }
});

/**
 * CENSORSHIP
 */
elements.censorAddBtn.addEventListener('click', e => {
    if(censorshipView.getAddBtnValue() === 'ion-ios-add-circle') {
        censorshipView.fillCensorForm();
    } else {
        censorshipView.clearCensorForm();
    }
});

//show phrases
elements.censorShowBtn.addEventListener('click', async e => {
    if(censorshipView.getShowBtnValue() === 'ion-ios-arrow-dropdown-circle') {
        if(state.user && state.user.user) {
            state.censorship = new Censorship();
            await state.censorship.getCensorship(state.user.user.username);
            censorshipView.clearList();
            censorshipView.renderPhrases(state.censorship.censors);
        }
    } else {
        censorshipView.clearList();
    }
});

//add phrase
elements.censorForm.addEventListener('submit', async e => {
    e.preventDefault();
    const phrase = censorshipView.getCensorInput();
    censorshipView.clearCensorInput();

    if(state.user && state.user.user) {
        state.censorship = new Censorship();
        await state.censorship.addCensorship(state.user.user.username, phrase);
        if(!state.censorship.error) {
            await state.censorship.getCensorship(state.user.user.username);
            censorshipView.clearList();
            censorshipView.renderPhrases(state.censorship.censors);
        } else {
            const errMessage = state.censorship.error.message;
            state.censorship.error = null;
            censorshipView.fillCensorFormError(errMessage);
        }
    }

    if(elements.noteShow.innerHTML && state.note && state.note.note) {
        await state.note.getNote(state.note.note.id, true);

        noteView.clearNoteView();
        noteView.fillShowNoteView(state.note.note);
    }
});

//delete phrase
elements.censorList.addEventListener('click', async e => {
    const el = e.target.closest('.censor-item');
    const btn = e.target.closest('.delete-btn');

    const phrase = el.id.split('_')[1];

    if(btn && state.censorship && state.user && state.user.user) {
        await state.censorship.deleteCensorship(state.user.user.username, phrase);
        await state.censorship.getCensorship(state.user.user.username);
        censorshipView.clearList();
        censorshipView.renderPhrases(state.censorship.censors);
    }

    if(elements.noteShow.innerHTML && state.note && state.note.note) {
        await state.note.getNote(state.note.note.id, true);

        noteView.clearNoteView();
        noteView.fillShowNoteView(state.note.note);
    }
});