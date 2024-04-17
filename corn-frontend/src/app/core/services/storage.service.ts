import { Injectable } from '@angular/core';
import { Project } from "@interfaces/boards/project";
import { StorageKey } from "@core/enum/storage-key.enum";

@Injectable({
    providedIn: 'root'
})
export class StorageService {

    getValueFromStorage<T>(key: StorageKey): T {
        let value: string | null = localStorage.getItem(key);

        if (value === null) {
            throw Error('Value not found in storage');
        }
        return value as T;
    }

    deleteProjectFromStorage(): void {
        localStorage.removeItem(StorageKey.PROJECT_ID);
        localStorage.removeItem(StorageKey.PROJECT_NAME);
    }

    saveProject(project: Project): void {
        localStorage.setItem(StorageKey.PROJECT_ID, project.projectId.toString());
        localStorage.setItem(StorageKey.PROJECT_NAME, project.name)
    }
}
