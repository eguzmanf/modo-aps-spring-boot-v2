export class ApiUsuarioCrear {

    constructor(urlGetComunaDelServicio, urlGetComunas, urlGetServiciosLaGranja, urlGetComunasLaGranja, urlGetServicios) {
        //
        this.urlGetComunaDelServicio = urlGetComunaDelServicio;
        this.urlGetComunas = urlGetComunas;
        this.urlGetServiciosLaGranja = urlGetServiciosLaGranja;
        this.urlGetComunasLaGranja = urlGetComunasLaGranja;
        this.token = document.querySelector('input[name=_csrf]').value;
        this.roleId = document.getElementById("rolePerfil.id");
        this.urlGetServicios = urlGetServicios;
    }

    async fetchComunaByIdDelSarvicio(servicioTargetValue) {
        //
        if(this.roleId.value === "2") {
            //
            console.log("Desde ApiUsuarioCrear :2: fetchComunaByIdDelSarvicio")

            let enviarDatos = {
                idServicioSalud: servicioTargetValue,
                idRole: this.roleId.value
            };

            const obtenerDatos = await fetch(this.urlGetComunaDelServicio, {
                method: 'POST',
                body: JSON.stringify(enviarDatos),
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'X-CSRF-TOKEN': this.token
                }
            });

            const resultado = await obtenerDatos.json();

            return {
                resultado
            };
        }
    }

    async fetchComunasByIdSarvicio(servicioTargetValue) {
        //
        if(this.roleId.value === "3") {
            //
            console.log("Desde ApiUsuarioCrear :3: fetchComunaByIdDelSarvicio")

            let enviarDatos = {
                idServicioSalud: servicioTargetValue,
                idRole: this.roleId.value
            };

            const obtenerDatos = await fetch(this.urlGetComunas, {
                method: 'POST',
                body: JSON.stringify(enviarDatos),
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'X-CSRF-TOKEN': this.token
                }
            });

            const resultado = await obtenerDatos.json();

            return {
                resultado
            };
        }
    }

    async fetchServiciosByIdRoleLaGranja(roleTargetValue) {
        //
        if(this.roleId.value === "4") {
            //
            console.log("Desde ApiUsuarioCrear :4: fetchServiciosByIdRoleLaGranja")

            let enviarDatos = {
                idRoleLaGranja: roleTargetValue,
                idRole: this.roleId.value
            };

            const obtenerDatos = await fetch(this.urlGetServiciosLaGranja, {
                method: 'POST',
                body: JSON.stringify(enviarDatos),
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'X-CSRF-TOKEN': this.token
                }
            });

            const resultado = await obtenerDatos.json();

            return {
                resultado
            };
        }
    }

    async fetchComunasByIdSarvicioLaGranja(servicioTargetValue) {
        //
        if(this.roleId.value === "4") {
            //
            console.log("Desde ApiUsuarioCrear :4: fetchComunasByIdSarvicioLaGranja")

            let enviarDatos = {
                idServicioSalud: servicioTargetValue,
                idRole: this.roleId.value
            };

            const obtenerDatos = await fetch(this.urlGetComunasLaGranja, {
                method: 'POST',
                body: JSON.stringify(enviarDatos),
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'X-CSRF-TOKEN': this.token
                }
            });

            const resultado = await obtenerDatos.json();

            return {
                resultado
            };
        }
    }

    async fetchServiciosListSelect() {
        //
        let enviarDatos = {
            idRole: this.roleId.value
        };

        const obtenerDatos = await fetch(this.urlGetServicios, {
            method: 'POST',
            body: JSON.stringify(enviarDatos),
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': this.token
            }
        });

        const resultado = await obtenerDatos.json();

        return {
            resultado
        }
    }
}