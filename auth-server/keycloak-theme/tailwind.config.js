const forms = require("@tailwindcss/forms");

module.exports = {
    content: ["./themes/corn/**/*.{ftl,html,js}"],
    plugins: [forms],
    theme: {
        extend: {
            colors: {
                yellowishDark: '#282727',
            },
        },
    }
};
