const URL = {
    blog: '/api/blog'
}


const articleToString = (article) => `
<div class="news_post">
<div class="post_meta">
${article.title}
</div>
<div class="post_image">
<img class="article_img" src="${article.picture}" alt="relevant picture">
</div>
<div class="post_text">
<p>${article.content.substring(0, 604)}</p>
</div>
<div class="collapse" id=${article.id}>
<div class="card card-body text-center">
<p>${article.content.substring(604)}</p>
</div>
</div>
<div class="post_image_box" type="button" data-toggle="collapse" data-target=#${article.id} aria-expanded="false" aria-controls="collapse">+</div>
</div>
`

fetch(URL.blog)
    .then(resp => resp.json())
    .then(articles => {
        let result = '';
        articles.forEach(article => {
            result += articleToString(article);
        })
        $('#blog_article').html(result);
    })
