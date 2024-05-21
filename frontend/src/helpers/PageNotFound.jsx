/**
 * PageNotFound component renders a page for displaying a 404 error message when a page is not found.
 * 
 * @returns {JSX.Element} The rendered 404 error page component.
 */

const PageNotFound = () => {
  return (
    <section class="flex items-center h-screen p-16 bg-gray-50 dark:bg-gray-700">
      <div class="container flex flex-col items-center ">
        <div class="flex flex-col gap-6 max-w-md text-center">
          <br /> {}
          <br /> {}
          <h2 class="font-extrabold text-9xl text-gray-600 dark:text-gray-100">
            <span class="sr-only">Error</span>404
          </h2>
          <p class="text-2xl md:text-3xl dark:text-gray-300">
            Sorry, we couldn't find this page.
          </p>
          <a
            href="/"
            class="px-8 py-4 text-xl font-semibold rounded bg-purple-600 text-gray-50 hover:text-gray-200"
          >
            Back to home page
          </a>
        </div>
      </div>
    </section>
  );
};

export default PageNotFound;
