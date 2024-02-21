import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private snackBar: MatSnackBar) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next
            .handle(request)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    if (error.status >= 400 && error.status < 500) {
                        this.openSnackBar(`There was a client side error! Check your request`, "Understood");
                    } else if (error.status >= 500 && error.status < 600) {
                        this.openSnackBar(`There was a server side error! Sorry for problems`, "Understood");
                    } else if (error.status < 200 && error.status > 300) {
                        this.openSnackBar(`App encountered an error!`, "Understood");
                    }
                    return throwError(() => error);
                })
            ) as Observable<HttpEvent<unknown>>;
    }

    private openSnackBar(message: string, action: string): void {
        this.snackBar.open(message, action, {
            horizontalPosition: "center",
            verticalPosition: "bottom",
        });
    }
}
