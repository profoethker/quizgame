import requests
import bs4
import pandas as pd

URL = ''


def load_page(url, data=None):
    if not url.startswith("http://"):
        url = URL + url
    #print("LOADING URL", url)
    if data is not None:
        req = requests.post(url, data=data)
    else:
        req = requests.get(url)
    assert req.ok, 'Request response was not ok!'
    return bs4.BeautifulSoup(req.text, features="lxml")


def _filter_for_php(href):
    return href and href.endswith('.php')

def get_php_urls(url):
    resp = load_page(url)
    raw_urls = [a.get('href') for a in resp.find_all('a')]
    return [u for u in raw_urls if u.endswith('.php') and 'impressum.php' not in u]

neighboring_php_urls = []
upper_php_urls = ['https://www.lebensmittel-quiz.de/2-obst/3-aepfel.php',
                  'https://www.lebensmittel-quiz.de/1-getreide/1-weizen.php',
                  'https://www.lebensmittel-quiz.de/3-getraenke/5-kaffee.php',
                  'https://www.lebensmittel-quiz.de/4-fette-oele/12-fette-oele.php',
                  'https://www.lebensmittel-quiz.de/5-kraeuter/13-kraeuter.php',
                  'https://www.ernaehrungsquiz.de/1-ernaehrungsmedizin/1-sporternaehrung.php',
                  'https://www.ernaehrungsquiz.de/2-vitamine/9-vitamin-a.php',
                  'https://www.ernaehrungsquiz.de/3-biochemie/16-enzyme.php']

for url in upper_php_urls:
    resp = load_page(url)
    all_phps = [a for a in resp.find_all(href=lambda h: h and h.endswith('.php'))]
    neighboring_php_urls.extend(set([a['href'] for a in all_phps if url.split('/')[-2] in a['href']]))
neighboring_php_urls = set(neighboring_php_urls)

q_a = []

for url in neighboring_php_urls:
    resp = load_page(url)
    questions = [q.text.split('.)')[-1].strip() for q in resp.find_all('h3')]
    answers = [a.next.strip() for a in resp.find_all(type='radio')]
    answers_packed = [[] for _ in questions]
    for i, a in enumerate(answers):
        answers_packed[i // 4].append(a)
    info_tags = [a for a in resp.find_all() if 'antwortText' in a.attrs.get('class', '')]
    info = [a.text for a in info_tags]
    correct_ans = [a.attrs['data-id'] for a in info_tags]

    q_a.extend([[q] + a + [i, c] for q, a, i, c in zip(questions, answers_packed, info, correct_ans)])

df = pd.DataFrame(q_a, columns=['Question', 'ans1', 'ans2', 'ans3', 'ans4', 'info', 'correct'])
df.to_csv('q_a.csv', index=False)
