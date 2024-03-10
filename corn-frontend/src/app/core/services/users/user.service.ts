import { Injectable } from '@angular/core';
import { User } from "@interfaces/boards/user";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor() {
    }

    mapToUser(user: any): User {
      return {
        userId: user.userId,
        name: user.name,
        surname: user.surname
      }
    }

}
