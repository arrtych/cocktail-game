# Use an official node image as a parent image
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install app dependencies
RUN npm install

# Copy the source code into the container
COPY . .

# Build the React app for production
RUN npm run build

# Use nginx to serve the build
FROM nginx:alpine

# Copy the build folder from the previous image
COPY --from=0 /app/build /usr/share/nginx/html

# Expose port 3000
EXPOSE 3000

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
