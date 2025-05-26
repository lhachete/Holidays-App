const loadForbiddenComponent = () =>
    import('../../errors/forbidden/forbidden.component');

const loadNotFoundComponent = () =>
    import('../../errors/not-found/not-found.component');

const loadAuthComponent = () =>
    import('../../components/auth/auth.component').then(m => m.AuthComponent);

const loadVacationComponent = () =>
    import('../../components/vacation/vacation.component').then(m => m.VacationComponent);

const loadProfileComponent = () =>
    import('../../components/profile/profile.component').then(m => m.ProfileComponent);

export {
    loadForbiddenComponent, loadNotFoundComponent, loadAuthComponent, loadVacationComponent, loadProfileComponent 
};

