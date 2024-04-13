import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class StorageService {

    constructor() {
    }

    saveProjectId(projectId: number): void {
        localStorage.setItem('projectId', projectId.toString());
    }

    getProjectId(): number {
        let id: string | null = localStorage.getItem('projectId');

        if (id === null) {
            throw Error('Project ID not found');
        }
        return parseInt(id);
    }

    deleteProjectId(): void {
        localStorage.removeItem('projectId');
    }
}
