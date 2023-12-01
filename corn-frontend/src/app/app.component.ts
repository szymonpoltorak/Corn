import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { Observable, catchError, of, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    display: string = '';

    public isLoggedIn = false;
    public userProfile: KeycloakProfile | null = null;

    constructor(
        private readonly keycloak: KeycloakService,
        private readonly http: HttpClient
    ) { }

    public async ngOnInit() {
        this.isLoggedIn = await this.keycloak.isLoggedIn();
        if (this.isLoggedIn) {
            this.userProfile = await this.keycloak.loadUserProfile();
        }
    }

    public login() {
        this.keycloak.login();
    }

    public logout() {
        this.keycloak.logout();
    }

    public authdump_update() {
        this.fetchApiUserinfo().subscribe(x => {
            this.display = x == null ? "IS NULL" : JSON.stringify(x);
        })
    }

    public fetchApiUserinfo(): Observable<User | null> {
        const ORIGIN = "http://localhost:8080";
        const USERINFO = "/api/userinfo";
        return this.http.get<User>(ORIGIN + USERINFO, {})
            .pipe(catchError(() => { return of(null); }))
            .pipe(take(1));
    }

}

export interface User {
    userId: number,
    name: string,
    surname: string,
    username: string,
    fullName: string,
}