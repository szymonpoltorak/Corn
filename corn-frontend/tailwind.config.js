/** @type {import('tailwindcss').Config} */

export const content = [
    "./src/**/*.{html,ts}",
];
export const theme = {
    extend: {
        colors: {
            'corn-primary': {
                50: '#ffe599',
                100: '#ffde7f',
                200: '#ffd866',
                300: '#ffd14c',
                400: '#ffcb32',
                500: '#ffc419',
                600: '#b28500',
                700: '#cc9800',
                800: '#e5ab00',
                900: '#ffbe00',
                A100: '#ffe599',
                A200: '#ffd866',
                A400: '#e5ab00',
                A700: '#ffbe00',
            },
            'dark-color-settings': {
                'main-background': '#1f1f1e',
                'container-background': '#292627',
                'font': '#ffffff',
            },
        },
        width: {
            "home-tab": "30rem"
        },
        fontFamily: {
            'logo': ['Helvetica Neue', 'sans-serif'],
        },
        zIndex: {
            "999": "999"
        }
    },
};
export const plugins = [];
