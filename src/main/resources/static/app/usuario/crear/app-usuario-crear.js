export class AppUsuarioCrear {

    constructor(uiCrear, apiCrear) {
        //
        this.uiCrear = uiCrear;
        this.apiCrear = apiCrear;
        //
        this.roleId = document.getElementById("rolePerfil.id");
        this.servicioComunaDiv = document.getElementById('servicioComunaDiv');
        this.servicioId = document.getElementById('servicioSalud.id');
        this.comunaCodigo = document.getElementById('comuna.codigoComuna');

        this.isCreateOrEdit = document.getElementById('isCreateOrEdit');
        this.hasErrorsScript = document.getElementById('hasErrorsScript');
        this.enabled = document.getElementById('enabled');
        this.badgeEnabled = document.getElementById('badge-enabled');

    }

    initAppCrear() {

        if(this.isCreateOrEdit.value === "true") {
            this.enabled.checked = true;
            this.badgeEnabled.innerHTML = 'Habilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');
        }
        console.log("Is checked: " + this.enabled.checked)
        console.log("isCreate: " + this.isCreateOrEdit.value)
    }

    domIsReadyAppCrear() {
        //
        if (document.readyState === 'complete') {

            if(this.hasErrorsScript.value === "true") {
                //
                console.log("Dentro de Form Errors: " + this.hasErrorsScript.value);

                if (this.roleId.selectedIndex === 1 && this.roleId.value === "1") { // ROLE_MINSAL
                    //
                    this.servicioId.selectedIndex = 0
                    this.comunaCodigo.selectedIndex = 0
                    this.servicioId.disabled = true;
                    this.comunaCodigo.disabled = true;
                    this.servicioComunaDiv.style.display = "none";

                } else if (this.roleId.selectedIndex === 2 && this.roleId.value === "2") {  // ROLE_SERVICIO
                    //
                    let servicioTargetValue = this.servicioId.value;
                    let codigoComunaValue = this.comunaCodigo.value;

                    this.apiCrear.fetchComunaByIdDelSarvicio(servicioTargetValue)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.comunaDelServicioChangeSelectList(dataObject, this.comunaCodigo);
                        });

                    this.servicioId.addEventListener('change', e => {
                        let servicioTargetValue = e.target.value;
                        console.log(servicioTargetValue);
                        this.apiCrear.fetchComunaByIdDelSarvicio(servicioTargetValue)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.comunaDelServicioChangeSelectList(dataObject, this.comunaCodigo);
                            });
                    });

                    this.roleId.addEventListener('change', e => {
                        let roleTargetValue = e.target.value;
                        console.log(roleTargetValue);

                        if (this.roleId.selectedIndex === 0) {
                            //
                            this.perfilIndexCeroSeleccionar();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 1 && this.roleId.value === "1" && roleTargetValue === "1") { // ROLE_MINSAL
                            //
                            this.perfilIndexUnoRoleMinsal();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 2 && this.roleId.value === "2" && roleTargetValue === "2") {  // ROLE_SERVICIO
                            //
                            this.perfilIndexDosRoleServicio();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 3 && this.roleId.value === "3" && roleTargetValue === "3") {    // ROLE_COMUNA
                            //
                            this.perfilIndexTresRoleComuna();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 4 && this.roleId.value === "4" && roleTargetValue === "4") {    // ROLE_LA_GRANJA
                            //
                            this.perfilIndexCuatroRoleLaGranja(roleTargetValue);
                        }
                    });

                } else if (this.roleId.selectedIndex === 3 && this.roleId.value === "3") {    // ROLE_COMUNA
                    //
                    console.log("Rol: ROLE_COMUNA");
                    let codigoComunaValue = this.comunaCodigo.value;

                    this.apiCrear.fetchComunasByIdSarvicio(this.servicioId.value)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, codigoComunaValue);
                        });

                    this.servicioId.addEventListener('change', e => {
                        let servicioTargetValue = e.target.value;
                        console.log(servicioTargetValue);
                        this.apiCrear.fetchComunasByIdSarvicio(servicioTargetValue)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, codigoComunaValue);
                            });
                    });

                    this.roleId.addEventListener('change', e => {
                        let roleTargetValue = e.target.value;
                        console.log(roleTargetValue);

                        if (this.roleId.selectedIndex === 0) {
                            //
                            this.perfilIndexCeroSeleccionar();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 1 && this.roleId.value === "1" && roleTargetValue === "1") { // ROLE_MINSAL
                            //
                            this.perfilIndexUnoRoleMinsal();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 2 && this.roleId.value === "2" && roleTargetValue === "2") {  // ROLE_SERVICIO
                            //
                            this.perfilIndexDosRoleServicio();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 3 && this.roleId.value === "3" && roleTargetValue === "3") {    // ROLE_COMUNA
                            //
                            this.perfilIndexTresRoleComuna(codigoComunaValue);
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 4 && this.roleId.value === "4" && roleTargetValue === "4") {    // ROLE_LA_GRANJA
                            //
                            this.perfilIndexCuatroRoleLaGranja(roleTargetValue);
                        }
                    });
                    
                } else if(this.roleId.selectedIndex === 4 && this.roleId.value === "4") {    // ROLE_LA_GRANJA
                    //
                    console.log("Hola ROLE_LA_GRANJA");
                    let codigoComunaValue = this.comunaCodigo.value;
                    let servicioIdValue = this.servicioId.value;

                        this.apiCrear.fetchServiciosByIdRoleLaGranja(this.roleId.value)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.serviciosLaGranjaByRoleChangeSelectList(dataObject, this.servicioId, servicioIdValue);
                                    this.apiCrear.fetchComunasByIdSarvicioLaGranja(servicioIdValue)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, codigoComunaValue);
                                        });
                            });

                    this.servicioId.addEventListener('change', e => {
                        let servicioTargetValue = e.target.value;
                        this.apiCrear.fetchComunasByIdSarvicioLaGranja(servicioTargetValue)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, codigoComunaValue);
                            });
                    });

                    this.roleId.addEventListener('change', e => {
                        let roleTargetValue = e.target.value;
                        console.log(roleTargetValue);

                        if (this.roleId.selectedIndex === 0) {
                            //
                            this.perfilIndexCeroSeleccionar();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 1 && this.roleId.value === "1" && roleTargetValue === "1") { // ROLE_MINSAL
                            //
                            this.perfilIndexUnoRoleMinsal();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 2 && this.roleId.value === "2" && roleTargetValue === "2") {  // ROLE_SERVICIO
                            //
                            this.perfilIndexDosRoleServicio();
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 3 && this.roleId.value === "3" && roleTargetValue === "3") {    // ROLE_COMUNA
                            //
                            this.perfilIndexTresRoleComuna(codigoComunaValue);
                            this.getServiciosListSelect();

                        } else if (this.roleId.selectedIndex === 4 && this.roleId.value === "4" && roleTargetValue === "4") {    // ROLE_LA_GRANJA
                            //
                            this.perfilIndexCuatroRoleLaGranja(roleTargetValue);
                        }
                    });
                }
            }
        }
    }

    getServicioChange() {
        //
        this.servicioId.addEventListener('change', e => {
            //
            if(this.servicioId.selectedIndex === 0) {
                //
                this.servicioComunaDiv.style.display = "";
                this.roleId.selectedIndex = 0
                this.servicioId.selectedIndex = 0
                this.comunaCodigo.selectedIndex = 0
                this.servicioId.disabled = true;
                this.comunaCodigo.disabled = true;
            }
        });
    }

    getComunaChange() {
        //
        this.comunaCodigo.addEventListener('change', e => {
            //
            if(this.comunaCodigo.selectedIndex === 0) {
                //
                this.servicioComunaDiv.style.display = "";
                this.roleId.selectedIndex = 0
                this.servicioId.selectedIndex = 0
                this.comunaCodigo.selectedIndex = 0
                this.servicioId.disabled = true;
                this.comunaCodigo.disabled = true;
            }
        });
    }

    getRoleIdChange() {
        //
        if(this.hasErrorsScript.value === "false") {
            //
            console.log("Dentro de Form Limpio: " + this.hasErrorsScript.value);

            this.roleId.addEventListener('change', e => {
                let roleTargetValue = e.target.value;
                console.log(roleTargetValue);

                if (this.roleId.selectedIndex === 0) {
                    //
                    this.perfilIndexCeroSeleccionar();
                    this.getServiciosListSelect();

                } else if (this.roleId.selectedIndex === 1 && this.roleId.value === "1" && roleTargetValue === "1") { // ROLE_MINSAL
                    //
                    this.perfilIndexUnoRoleMinsal();
                    this.getServiciosListSelect();

                } else if (this.roleId.selectedIndex === 2 && this.roleId.value === "2" && roleTargetValue === "2") {  // ROLE_SERVICIO
                    //
                    this.perfilIndexDosRoleServicio();
                    this.getServiciosListSelect();

                } else if (this.roleId.selectedIndex === 3 && this.roleId.value === "3" && roleTargetValue === "3") {    // ROLE_COMUNA
                    //
                    this.perfilIndexTresRoleComuna();
                    this.getServiciosListSelect();

                } else if (this.roleId.selectedIndex === 4 && this.roleId.value === "4" && roleTargetValue === "4") {    // ROLE_LA_GRANJA
                    //
                    this.perfilIndexCuatroRoleLaGranja(roleTargetValue);
                }
            });
        }
    }

    perfilIndexCeroSeleccionar() {
        this.servicioComunaDiv.style.display = "";
        this.servicioId.selectedIndex = 0
        this.comunaCodigo.selectedIndex = 0
        this.servicioId.disabled = true;
        this.comunaCodigo.disabled = true;
    }

    perfilIndexUnoRoleMinsal() {
        this.servicioId.selectedIndex = 0
        this.comunaCodigo.selectedIndex = 0
        this.servicioId.disabled = true;
        this.comunaCodigo.disabled = true;
        this.servicioComunaDiv.style.display = "none";
    }

    perfilIndexDosRoleServicio() {
        this.servicioComunaDiv.style.display = "";
        this.servicioId.selectedIndex = 0
        this.comunaCodigo.selectedIndex = 0
        this.servicioId.disabled = false;
        this.comunaCodigo.disabled = false;

        this.servicioId.addEventListener('change', e => {
            let servicioTargetValue = e.target.value;
            this.apiCrear.fetchComunaByIdDelSarvicio(servicioTargetValue)
                .then(data => {
                    let dataObject = data.resultado;
                    console.log(dataObject);
                    this.comunaDelServicioChangeSelectList(dataObject, this.comunaCodigo);
                });
        });
    }

    perfilIndexTresRoleComuna() {
        this.servicioComunaDiv.style.display = "";
        this.servicioId.selectedIndex = 0
        this.comunaCodigo.selectedIndex = 0
        this.servicioId.disabled = false;
        this.comunaCodigo.disabled = false;

        this.servicioId.addEventListener('change', e => {
            let servicioTargetValue = e.target.value;
            this.apiCrear.fetchComunasByIdSarvicio(servicioTargetValue)
                .then(data => {
                    let dataObject = data.resultado;
                    console.log(dataObject);
                    this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo);
                });
        });
    }

    getServiciosListSelect(){
        this.apiCrear.fetchServiciosListSelect()
            .then(data => {
                let dataObject = data.resultado;
                console.log(dataObject);
                this.serviciosSelectList(dataObject, this.servicioId)
            })
    }

    perfilIndexCuatroRoleLaGranja(roleTargetValue) {
        this.servicioComunaDiv.style.display = "";
        this.servicioId.selectedIndex = 0
        this.comunaCodigo.selectedIndex = 0
        this.servicioId.disabled = false;
        this.comunaCodigo.disabled = false;

        this.apiCrear.fetchServiciosByIdRoleLaGranja(roleTargetValue)
            .then(data => {
                let dataObject = data.resultado;
                console.log(dataObject);
                this.serviciosLaGranjaByRoleChangeSelectList(dataObject, this.servicioId);

                this.servicioId.addEventListener('change', e => {
                    let servicioTargetValue = e.target.value;
                    this.apiCrear.fetchComunasByIdSarvicioLaGranja(servicioTargetValue)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo);
                        });
                });
            });
    }

    serviciosSelectList(dataObject, objectHtmlSelect) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].servicioSalud;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });
    }

    comunaDelServicioChangeSelectList(dataObject, objectHtmlSelect) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;
        $('#comuna.codigoComuna option').remove();

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        option = document.createElement('option');
        option.text = dataObject.comunaNombre;
        option.value = dataObject.comuna.codigoComuna;
        objectHS.add(option);
        objectHS.selectedIndex = 1;
    }

    comunasByServicioChangeSelectList(dataObject, objectHtmlSelect, codigoComunaValue) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].comuna;
            option.value = dataObject[key].codigoComuna;

            if(codigoComunaValue == dataObject[key].codigoComuna) {
                keyPlus = (parseInt(key) + 1).toString()
            }

            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    serviciosLaGranjaByRoleChangeSelectList(dataObject, objectHtmlSelect, servicioIdValue) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].servicioSalud;
            option.value = dataObject[key].id;
            if(servicioIdValue == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    enabledOrDisableUser() {
        //
        this.enabled.addEventListener('change', (e) => {
            //
            if (this.enabled.checked) {
                //
                this.badgeEnabled.innerHTML = 'Habilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');


            } else {
                //
                this.badgeEnabled.innerHTML = 'Inhabilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
            }
        });
    }

    isEnabled() {
        if (this.enabled.checked) {
            //
            this.badgeEnabled.innerHTML = 'Habilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');


        } else {
            //
            this.badgeEnabled.innerHTML = 'Inhabilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
        }
    }

    eyesPasswordChangeModal() {
        const passwordChangeModal = document.getElementById('passwordModal');
        const togglePasswordEyesModal = document.querySelector('#togglePasswordModal');
        togglePasswordEyesModal.addEventListener('click', function (e) {
            const type = passwordChangeModal.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordChangeModal.setAttribute('type', type);
            this.classList.toggle('fa-eye-slash');
        });
    }

    eyesPasswordCrear() {
        const passwordCreate = document.getElementById('password');
        const togglePasswordEyes = document.querySelector('#togglePassword');
        if(togglePasswordEyes != null) {
            togglePasswordEyes.addEventListener('click', function (e) {
                if (passwordCreate.type === "password") {
                    passwordCreate.type = "text";
                } else {
                    passwordCreate.type = "password";
                }
                this.classList.toggle('fa-eye-slash');
            });
        }
    }
}